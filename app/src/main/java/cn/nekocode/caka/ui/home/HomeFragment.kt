/*
 * Copyright 2019. nekocode (nekocode.cn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.nekocode.caka.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cn.nekocode.caka.R
import cn.nekocode.caka.base.BaseFragment
import cn.nekocode.caka.databinding.FragmentHomeBinding

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val vm by lazy {
        getViewModel(HomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        val adapter = ReposAdapter { repo ->
            findNavController().navigate(HomeFragmentDirections.actionHomeToRepo(repo))
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE

        vm.reposLiveData.observe(viewLifecycleOwner, Observer { repos ->
            adapter.submitList(repos)
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        })
    }
}
