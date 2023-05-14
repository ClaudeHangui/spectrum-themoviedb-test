package com.example.spectrum_themoviedb_test

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import app.cash.turbine.test
import com.example.spectrum_themoviedb_test.util.InternetStateManager
import com.example.spectrum_themoviedb_test.util.SdkManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class InternetStateManagerTest {

    @MockK
    lateinit var connectivityManager: ConnectivityManager
    @MockK
    lateinit var network: Network
    @MockK
    lateinit var networkRequest: NetworkRequest.Builder
    @MockK
    lateinit var sdkManager: SdkManager
    private lateinit var internetStateManager: InternetStateManager
    private val callbackSlot = slot<ConnectivityManager.NetworkCallback>()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        internetStateManager = InternetStateManager(connectivityManager, networkRequest, sdkManager)
    }

    @Test
    fun `when internet is available then return true`() = runTest {
        coEvery { sdkManager.isSdkVersionAtLeast(24) } returns true

        coEvery {
            connectivityManager.registerDefaultNetworkCallback(capture(callbackSlot))
        } answers {
            callbackSlot.captured.onAvailable(network)
        }

        justRun { connectivityManager.unregisterNetworkCallback(any<ConnectivityManager.NetworkCallback>()) }

        internetStateManager.observeNetworkChangeState().test {
            advanceUntilIdle()
            awaitItem() shouldBe true
        }

        coVerify { connectivityManager.unregisterNetworkCallback(callbackSlot.captured) }
    }

    @Test
    fun `when internet is unavailable return false`() = runTest {
        coEvery { sdkManager.isSdkVersionAtLeast(24) } returns true

        coEvery {
            connectivityManager.registerDefaultNetworkCallback(capture(callbackSlot))
        } answers {
            callbackSlot.captured.onUnavailable()
        }

        justRun { connectivityManager.unregisterNetworkCallback(any<ConnectivityManager.NetworkCallback>()) }
        internetStateManager.observeNetworkChangeState().test {
            advanceUntilIdle()
            awaitItem() shouldBe false
        }

        coVerify { connectivityManager.unregisterNetworkCallback(callbackSlot.captured) }
    }

    @Test
    fun `when internet is lost return false`() = runTest {
        coEvery { sdkManager.isSdkVersionAtLeast(24) } returns true
        val callbackSlot = slot<ConnectivityManager.NetworkCallback>()
        justRun { connectivityManager.registerDefaultNetworkCallback(capture(callbackSlot)) }
        justRun { connectivityManager.unregisterNetworkCallback(any<ConnectivityManager.NetworkCallback>()) }
        internetStateManager.observeNetworkChangeState().test {
            advanceUntilIdle()
            callbackSlot.captured.onLost(network)
            awaitItem() shouldBe false
        }

        coVerify { connectivityManager.unregisterNetworkCallback(callbackSlot.captured) }
    }
}