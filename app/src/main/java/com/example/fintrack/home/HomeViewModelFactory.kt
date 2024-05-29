import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fintrack.home.HomeViewModel
import com.example.fintrack.repo.ExpenseRepository

class HomeViewModelFactory(private val repository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
