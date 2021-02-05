/*
 * Copyright 2021. nekocode (nekocode.cn@gmail.com)
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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.nekocode.caka.R
import cn.nekocode.caka.backend.model.Repository
import cn.nekocode.caka.databinding.ItemRepoBinding

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class ReposAdapter(
    private val onClick: (Repository) -> Unit
) : ListAdapter<Repository, ReposAdapter.ViewHolder>(RepositoryDiffCallback) {

    inner class ViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentRepo: Repository? = null

        init {
            itemView.setOnClickListener {
                currentRepo?.let {
                    onClick(it)
                }
            }
        }

        fun bind(repo: Repository) {
            currentRepo = repo

            val context = itemView.context
            binding.tvTitle.text = repo.name
            binding.tvBody.text = repo.description
            binding.tvBottom.text = context.getString(
                R.string.repo_bottom_text,
                repo.stargazersCount,
                repo.forksCount
            )
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_repo, viewGroup, false)
        return ViewHolder(ItemRepoBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val repo = getItem(position)
        viewHolder.bind(repo)
    }
}

object RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }
}