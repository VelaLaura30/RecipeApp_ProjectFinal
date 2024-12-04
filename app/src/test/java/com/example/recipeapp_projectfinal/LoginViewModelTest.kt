package com.example.recipeapp_projectfinal
import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.recipeapp_projectfinal.data.viewmodel.LoginViewModel
import com.example.recipeapp_projectfinal.db.User
import com.example.recipeapp_projectfinal.db.UserDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class LoginViewModelTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var userDao: UserDao
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)


        userDao = mock(UserDao::class.java)
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)


        `when`(context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)


        viewModel = LoginViewModel(userDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `authenticate with valid credentials updates isAuthenticated to true`() = runTest {

        val mockUser = User(email = "test@example.com", password = "password123", name = "Test User")
        `when`(userDao.getUserByEmail("test@example.com")).thenReturn(mockUser)


        viewModel.authenticate(context, "test@example.com", "password123")


        advanceUntilIdle()


        assertTrue(viewModel.isAuthenticated)
        assertEquals("", viewModel.errorMessage)
        verify(editor).putString("user_name", "Test User")
        verify(editor).apply()
    }

    @Test
    fun `authenticate with invalid credentials updates isAuthenticated to false`() = runTest {

        `when`(userDao.getUserByEmail("test@example.com")).thenReturn(null)

        viewModel.authenticate(context, "test@example.com", "wrongpassword")


        advanceUntilIdle()


        assertFalse(viewModel.isAuthenticated)
        assertEquals("Invalid email or password", viewModel.errorMessage)
        verify(editor, never()).putString(anyString(), anyString())
    }

    @Test
    fun `authenticate with exception updates errorMessage`() = runTest {

        `when`(userDao.getUserByEmail(anyString())).thenThrow(RuntimeException("Database error"))

        viewModel.authenticate(context, "test@example.com", "password123")


        advanceUntilIdle()

        assertFalse(viewModel.isAuthenticated)
        assertTrue(viewModel.errorMessage.startsWith("Error occurred:"))
        verify(editor, never()).putString(anyString(), anyString())
    }
}
