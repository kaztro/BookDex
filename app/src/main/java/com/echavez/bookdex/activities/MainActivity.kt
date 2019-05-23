package com.echavez.bookdex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.echavez.bookdex.Adapter.BooksAdapter
import com.echavez.bookdex.R
import com.echavez.bookdex.ViewModel.BookViewModel
import com.echavez.bookdex.entities.Book
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var bookViewModel: BookViewModel
    private var flag = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bookAdapter = BooksAdapter(this) { book:Book->bookFavorito(book)}
        rvLibritos.adapter = bookAdapter
        rvLibritos.layoutManager = LinearLayoutManager(this)

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)



        bookViewModel.allBooks.observe(this, Observer { books ->
            bookAdapter.setBooks(books)
        })

        Log.v("cantidad", bookAdapter.itemCount.toString())



        btPrueba.setOnClickListener() {
            if (flag) {
                bookViewModel.favBooks.observe(this, Observer { favBooks ->
                    bookAdapter.setBooks(favBooks)
                })
                btPrueba.text = "Cambiar a vista de todos"
            } else {
                bookViewModel.allBooks.observe(this, Observer { books ->
                    bookAdapter.setBooks(books)
                })
                btPrueba.text = "Cambiar a Favoritos"
            }
            flag = !flag
        }

        BtBuscar.setOnClickListener(){

            bookViewModel.getBooksbyTag(ETbuscar.text.toString())

            bookViewModel.booksByTag.observe(this, Observer { books->
                bookAdapter.setBooks(books)
            })

        }
    }

    private fun bookFavorito(book: Book) {
        bookViewModel.marcarODesmarcarFav(book)
    }

}
