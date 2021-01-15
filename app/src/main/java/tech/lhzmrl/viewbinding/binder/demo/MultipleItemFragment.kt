package tech.lhzmrl.viewbinding.binder.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.lhzmrl.bind.viewbinding.demo.databinding.FragmentMultipleItemBinding
import tech.lhzmrl.viewbinding.binder.annotation.ViewBinding
import tech.lhzmrl.viewbinding.binder.demo.dummy.DummyContent

class MultipleItemFragment : Fragment() {

    @ViewBinding
    private var _binding: FragmentMultipleItemBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_multiple_item, container, false)
        _binding = FragmentMultipleItemBinding.bind(view)

        binding.rvList.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        binding.rvList.adapter = MultipleItemRecyclerViewAdapter(DummyContent.ITEMS)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            MultipleItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }

    }
}