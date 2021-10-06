package com.tokopedia.workshopnovember.repo

import com.tokopedia.workshopnovember.pojo.search.Doc
import com.tokopedia.workshopnovember.repo.cloud.BookApi

//class FakeBookApi: BookApi {
//    override suspend fun search(query: String): SearchResult {
//        return SearchResult(
//            docs = listOf(Doc(
//                title = "Harry Potter",
//                author_name = listOf("JK Rowling"),
//                ...
//            )),
//            q = query,
//            num_found = 20,
//            ...
//        )
//    }
//}