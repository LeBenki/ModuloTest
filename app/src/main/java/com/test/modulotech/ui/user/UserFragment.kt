package com.test.modulotech.ui.user

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.test.modulotech.R
import com.test.modulotech.databinding.FragmentUserBinding
import com.test.modulotech.model.Address
import com.test.modulotech.model.User
import com.test.modulotech.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_user_edit.view.*
import java.text.SimpleDateFormat
import java.util.*


class UserFragment : Fragment() {

    private var edit: MenuItem? = null
    private var save: MenuItem? = null
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentUserBinding>(
            inflater,
            R.layout.fragment_user,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getIsEditMode().observe(viewLifecycleOwner, { isEditing ->
            if (isEditing) {
                edit?.isVisible = false
                save?.isVisible = true
            } else {
                edit?.isVisible = true
                save?.isVisible = false
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDatePicker()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initDatePicker() {
        val myCalendar = Calendar.getInstance()

        val date =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel(myCalendar)
            }

        view?.birthDate?.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                DatePickerDialog(
                    requireContext(), date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
                true
            }
            else
                false
        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val sdf = SimpleDateFormat(dateFormat)
        view?.birthDate?.setText(sdf.format(myCalendar.time))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.user, menu)

        save = menu.findItem(R.id.menu_save)
        edit = menu.findItem(R.id.menu_edit)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                viewModel.changeEditMode(false)
                saveUser()
                true
            }
            R.id.menu_edit -> {
                viewModel.changeEditMode(true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveUser() {
        val sdf = SimpleDateFormat(dateFormat)
        val mDate: Date = sdf.parse(view?.birthDate?.text.toString())
        val timeInMilliseconds: Long = mDate.time

        val user = User(
            view?.firstName?.text.toString(),
            view?.lastName?.text.toString(),
            Address(
                view?.city?.text.toString(),
                view?.postalCode?.text.toString().toInt(),
                view?.street?.text.toString(),
                view?.streetCode?.text.toString(),
                view?.postalCode?.text.toString()
            ),
            timeInMilliseconds
        )
        viewModel.editUser(user)
    }

    override fun onPause() {
        super.onPause()
        viewModel.changeEditMode(false)
    }

    companion object {
        const val dateFormat = "dd/MM/yyyy"
    }
}