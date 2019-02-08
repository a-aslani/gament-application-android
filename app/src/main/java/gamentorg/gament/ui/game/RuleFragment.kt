package gamentorg.gament.ui.game


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.vm.RuleViewModel
import gamentorg.gament.vm.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_rule.view.*
import javax.inject.Inject

class RuleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var ruleViewModel: RuleViewModel

    private lateinit var ruleKey: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rule, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ruleViewModel = ViewModelProviders.of(this, viewModelFactory).get(RuleViewModel::class.java)

        ruleKey = arguments!!.getString("rule_key")!!

        ruleViewModel.getRuleByKey(ruleKey).observe(this, Observer {
            if (it != null) {
                view.rule_txt_description.text = it.description
            }
        })


    }
}
