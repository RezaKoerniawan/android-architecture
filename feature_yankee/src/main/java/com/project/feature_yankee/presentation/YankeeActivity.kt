package com.project.feature_yankee.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.project.data.PrefManager
import com.project.feature_yankee.R
import com.project.feature_yankee.databinding.ActivityYankeeBinding
import com.project.framework.core.BaseActivity
import com.project.framework.core.owner.ViewDataBindingOwner
import com.project.framework.navigation.Navigation
import com.project.framework.navigation.NavigationEvent
import org.greenrobot.eventbus.EventBus

class YankeeActivity : BaseActivity(),
    YankeeView,
    ViewDataBindingOwner<ActivityYankeeBinding> {

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_yankee
    }

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, YankeeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override lateinit var binding: ActivityYankeeBinding
    lateinit var viewModel: YankeeViewModel
//    val viewModel by lazy {getViewModel(YankeeViewModel::class)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = YankeeViewModel()
        viewModel = binding.vm!!
//        loadKoinModules(yankeeModule)

        initUI()
    }

    private fun initUI() {
        title = getString(R.string.msg_yankee_title)

        viewModel.textPref.set("Preference: Do you like Math? ${PrefManager.isLikeMath}")
    }

    override fun onClickChangePref(view: View) {
        PrefManager.isLikeMath = !PrefManager.isLikeMath
        viewModel.textPref.set("Preference: Do you like Math? ${PrefManager.isLikeMath}")
    }

    override fun onClickGotoFeatureZulu(view: View) {
        val event = NavigationEvent(this, Navigation.FEATURE_B)
        val bundle = Bundle()
        bundle.putString("TEXT_TEST", viewModel.stringData.get())
        event.bundle = bundle
        EventBus.getDefault().post(event)
    }

    override fun onDestroy() {
        super.onDestroy()
//        unloadKoinModules(yankeeModule)
    }
}