package com.filippoengidashet.johnlewis.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 12:01.
 */
open class BaseFragment : Fragment {

    constructor() : super()

    constructor(layoutId: Int) : super(layoutId)

    protected fun showToast(message: String?, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, length).show()
    }
}
