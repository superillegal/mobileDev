package com.example.application102;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTitle, editAuthor, editYear, editGenre;
    private Button btnAdd, btnFind, btnUpdate, btnDelete, btnResetSearch;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitle = findViewById(R.id.editTitle);
        editAuthor = findViewById(R.id.editAuthor);
        editYear = findViewById(R.id.editYear);
        editGenre = findViewById(R.id.editGenre);
        btnAdd = findViewById(R.id.btnAdd);
        btnFind = findViewById(R.id.btnFind);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnResetSearch = findViewById(R.id.btnResetSearch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        loadBookData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findBook();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBook();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });

        btnResetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputFields();
                loadBookData();
            }
        });
    }

    private void loadBookData() {
        List<Book> books = dbHelper.getAllBooks();
        adapter = new BookAdapter(books, dbHelper, this);
        recyclerView.setAdapter(adapter);
    }

    private void addBook() {
        String title = editTitle.getText().toString();
        String author = editAuthor.getText().toString();
        int year = Integer.parseInt(editYear.getText().toString());
        String genre = editGenre.getText().toString();

        if (dbHelper.addBook(new Book(0, title, author, year, genre))) {
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
            loadBookData();
            clearInputFields();
        } else {
            Toast.makeText(this, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        }
    }

    private void findBook() {
        String title = editTitle.getText().toString();
        String author = editAuthor.getText().toString();
        String yearString = editYear.getText().toString();
        String genre = editGenre.getText().toString();

        List<Book> foundBooks = new ArrayList<>();

        if (!title.isEmpty()) {
            foundBooks.addAll(dbHelper.findBooksByTitle(title));
        }
        if (!author.isEmpty()) {
            foundBooks.addAll(dbHelper.findBooksByAuthor(author));
        }
        if (!yearString.isEmpty()) {
            int year = Integer.parseInt(yearString);
            foundBooks.addAll(dbHelper.findBooksByYear(year));
        }
        if (!genre.isEmpty()) {
            foundBooks.addAll(dbHelper.findBooksByGenre(genre));
        }

        if (foundBooks.isEmpty()) {
            Toast.makeText(this, "Книги не найдены", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new BookAdapter(foundBooks, dbHelper, this);
            recyclerView.setAdapter(adapter);
        }
    }

    private void updateBook() {
        String title = editTitle.getText().toString();
        String author = editAuthor.getText().toString();
        int year = Integer.parseInt(editYear.getText().toString());
        String genre = editGenre.getText().toString();

        Book book = dbHelper.findBookByTitle(title);
        if (book != null) {
            book.setAuthor(author);
            book.setYear(year);
            book.setGenre(genre);
            if (dbHelper.updateBook(book)) {
                Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
                loadBookData();
                clearInputFields();
            } else {
                Toast.makeText(this, "Ошибка обновления", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook() {
        String title = editTitle.getText().toString();
        Book book = dbHelper.findBookByTitle(title);
        if (book != null) {
            if (dbHelper.deleteBook(book.getId())) {
                Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
                loadBookData();
                clearInputFields();
            } else {
                Toast.makeText(this, "Ошибка удаления", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputFields() {
        editTitle.setText("");
        editAuthor.setText("");
        editYear.setText("");
        editGenre.setText("");
    }
}
