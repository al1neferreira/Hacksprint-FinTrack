package com.example.fintrack.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import com.example.fintrack.R
import com.example.fintrack.databinding.FragmentCreatExpenseBinding

class CreatExpenseFragment : Fragment(R.layout.fragment_creat_expense), MenuProvider {

    private var creatExpenseBinding: FragmentCreatExpenseBinding? = null
    private val binding get() = creatExpenseBinding!!

    //inserir viewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        creatExpenseBinding = FragmentCreatExpenseBinding.inflate(inflater, container, false)

        /*val spinner: Spinner = binding.spinnerCategories
        ArrayAdapter.createFromResource(
            this, R.array.categories_array, android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }

         */

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        TODO("Not yet implemented")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}