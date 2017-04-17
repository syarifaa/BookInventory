package com.example.syarifaa.bookinventory;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.syarifaa.bookinventory.activity.Book;
import com.example.syarifaa.bookinventory.activity.NewBook;
import com.example.syarifaa.bookinventory.adapter.BooksAdapter;
import com.example.syarifaa.bookinventory.adapter.DividerDecoration;
import com.example.syarifaa.bookinventory.helper.HelperFunction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public int TO_FORM = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerBook)
    RecyclerView recyclerBook;
    @BindView(R.id.fab)
    FloatingActionButton btnAdd;
    private List<Book> bookList = new ArrayList<Book>();
    private BooksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Books Catalog");

        mAdapter = new BooksAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerBook.setLayoutManager(mLayoutManager);
        recyclerBook.setItemAnimator(new DefaultItemAnimator());
        recyclerBook.addItemDecoration(new DividerDecoration(this));

        recyclerBook.setAdapter(mAdapter);
        recyclerBook.addOnItemTouchListener(new HelperFunction.
                RecyclerTouchListener(this, recyclerBook,
                new HelperFunction.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent i = new Intent(MainActivity.this, NewBook.class);
                        i.putExtra("bookEdit", bookList.get(position));
                        startActivity(i);
                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        final Book book = bookList.get(position);
                        AlertDialog dialog = new AlertDialog.
                                Builder(MainActivity.this).setTitle("Delete")
                                .setMessage("Are you sure to delete " + book.getBook_title() + " ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        bookList.remove(book);
                                        mAdapter.notifyItemRemoved(position);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .create();
                        dialog.show();
                    }
                }));

        prepareBookData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewBook.class);
                startActivityForResult(i, TO_FORM);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                SearchView searchView = (SearchView) item.getActionView();

                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                searchView.setOnQueryTextListener(MainActivity.this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TO_FORM) {
            Book bookForm = (Book) data.getExtras().getSerializable("book");
            bookList.add(bookForm);
            Toast.makeText(this, "Book " + bookForm.getBook_title() + " successfully added", Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void prepareBookData() {
        Book book = new Book("9780439064873", "Harry Potter And The Chamber Of Secrets", "J.K. Rowling", "Fantasy, Thriller, Mystery", "The Dursleys were so mean and hideous that summer that all Harry Potter wanted was to get back to the Hogwarts School for Witchcraft and Wizardry. But just as he's packing his bags, Harry receives a warning from a strange, impish creature named Dobby who says that if Harry Potter returns to Hogwarts, disaster will strike.\n" +
                "\n" +
                "And strike it does. For in Harry's second year at Hogwarts, fresh torments and horrors arise, including an outrageously stuck-up new professor, Gilderoy Lockhart, a spirit named Moaning Myrtle who haunts the girls' bathroom, and the unwanted attentions of Ron Weasley's younger sister, Ginny. But each of these seem minor annoyances when the real trouble begins, and someone, or something, starts turning Hogwarts students to stone. Could it be Draco Malfoy, a more poisonous rival than ever? Could it possibly be Hagrid, whose mysterious past is finally told? Or could it be the one everyone at Hogwarts most suspects: Harry Potter himself?", 2000, R.drawable.hp_chamber);
        bookList.add(book);

        book = new Book("9780316015844", "Twilight (The Twilight Saga, Book 1)", "Stephanie Meyer", "Fantasy, Drama, Romance", "This book consists of articles from Wikia. Commentary (films not included). Pages: 42. Chapters: Actors, Book vs. movie, Breaking Dawn films, Composers, Directors, Eclipse film, Film images, New Moon film, Twilight film, Videos, Breaking Dawn, Breaking Dawn Part I soundtrack, Alexandre Desplat, Carter Burwell, Howard Shore, Bill Condon, Catherine Hardwicke, Chris Weitz, David Slade, Eclipse, Eclipse movie quotes, Eclipse Release, Eclipse score, Eclipse soundtrack, Cullen Crest, Easter eggs, Summit Entertainment, New Moon, New Moon movie quotes, New Moon movie reviews, New Moon score, New Moon soundtrack, Twilight, Twilight movie quotes, Twilight movie reviews, Twilight premiere, Twilight soundtrack. Excerpt: The following are noted differences between the original Eclipse novel and the movie. Please Note: Changes are not in any particular order. The following are noted differences between the New Moon novel and movie adaptation. Please Note: Additions and changes are not in any particular order. The following are noted differences between the original Twilight novel and the movie adaptation. Scenes/lines that were not in the book but were added for the movie: Scenes/lines from the book that were left out of the movie: Summit Entertainment, the studio behind the film adaptations of novels in the Twilight series, announced in November 2008 that it had obtained the rights to the rest of Stephenie Meyer's series, including Breaking Dawn.Breaking Dawn has been confirmed to be split in two parts. The release date of the first half of Breaking Dawn is set on November 18, 2011 while the second half is set on November 16, 2012. On August 18, 2011, Summit Entertainment released the official synopsis of the film in its entirety: The highly anticipated fourth installment of The Twilight Saga, directed by Academy Award winner Bill Condon, the Twilight Saga: Breaking Dawn - Part 1 reveals the mysteries of this romantic epic that has entranced millions. In The Twilight Sag...", 2006, R.drawable.twilight1);
        bookList.add(book);

        book = new Book("9781484724989", "Journey to Star Wars: The Force Awakens Lost Stars", "Claudia Gray", "Action, Thriller, Science Fiction", "Eight years after the fall of the Old Republic, the Galactic Empire now reigns over the known galaxy. Resistance to the Empire has been all but silenced. Only a few courageous leaders such as Bail Organa of Alderaan still dare to openly oppose Emperor Palpatine.\n" +
                "\n" +
                "After years of defiance, the many worlds at the edge of the Outer Rim have surrendered. With each planet’s conquest, the Empire’s might grows stronger.\n" +
                "\n" +
                "The latest to fall under the Emperor’s control is the isolated mountain planet Jelucan, whose citizens hope for a more prosperous future even as the Imperial Starfleet gathers overhead…", 2015, R.drawable.star_wars);
        bookList.add(book);

        book = new Book("9780439136365", "Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", "Fantasy, Thriller, Mystery", "Harry Potter is lucky to reach the age of thirteen, since he has already survived the murderous attacks of the feared Dark Lord on more than one occasion. But his hopes for a quiet term concentrating on Quidditch are dashed when a maniacal mass-murderer escapes from Azkaban, pursued by the soul-sucking Dementors who guard the prison. It's assumed that Hogwarts is the safest place for Harry to be. But is it a coincidence that he can feel eyes watching him in the dark, and should he be taking Professor Trelawney's ghoulish predictions seriously?", 2001, R.drawable.hp_azkaban);
        bookList.add(book);

        mAdapter.notifyDataSetChanged();
    }
}