package com.ankoma88.rxsample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends Activity {
  private static final String LOG_TAG = "MainActivity";

  private ListView listView;
  private List<ListItem> data = new ArrayList<>();
  private Subscriber subscriber;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listview);
    findViews();

  }

  private void findViews() {
    listView = (ListView) findViewById(R.id.lvItems);

    Button btnLoadUnmodified = (Button) findViewById(R.id.btnLoadUnmodified);
    Button btnFilter = (Button) findViewById(R.id.btnFilter);
    Button btnTakeLast = (Button) findViewById(R.id.btnTakeLast);
    Button btnLast = (Button) findViewById(R.id.btnlLast);
    Button btnSkip = (Button) findViewById(R.id.btnSkip);
    Button btnDistinct = (Button) findViewById(R.id.btnDistinct);
    Button btnOfType = (Button) findViewById(R.id.btnOfType);
    Button btnElementAt = (Button) findViewById(R.id.btnElementAt);

    btnLoadUnmodified.setOnClickListener(v -> loadDataUnmodified());
    btnFilter.setOnClickListener(v -> doFilter());
    btnTakeLast.setOnClickListener(v -> doTakeLast());
    btnLast.setOnClickListener(v -> doLast());
    btnSkip.setOnClickListener(v -> doSkip());
    btnDistinct.setOnClickListener(v -> doDistinct());
    btnOfType.setOnClickListener(v -> doOfType());
    btnElementAt.setOnClickListener(v -> doElementAt());
  }

  private void showData() {
    MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.list_item);
    adapter.addAll(data);
    listView.setAdapter(adapter);
  }

  private void initSubscriber() {
    subscriber = new Subscriber<ListItem>() {
      @Override
      public void onCompleted() {
        showData();
      }

      @Override
      public void onError(Throwable e) {
        Log.e(LOG_TAG, e.getMessage());
        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNext(ListItem listItem) {
        data.add(listItem);
      }
    };
  }

  private void loadDataUnmodified() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber);

    compositeSubscription.add(subscription);
  }


  private void doFilter() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .filter(listItem -> Integer.valueOf(listItem.getLine2()) >= 1990)
        .subscribe(subscriber);
    compositeSubscription.add(subscription);
  }

  private void doTakeLast() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .takeLast(5)
        .subscribe(subscriber);
    compositeSubscription.add(subscription);
  }

  private void doLast() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .last(listItem -> listItem.getLine1().equals("Anton"))
        .subscribe(subscriber);
    compositeSubscription.add(subscription);
  }

  private void doSkip() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .skip(5)
        .subscribe(subscriber);
    compositeSubscription.add(subscription);
  }

  private void doDistinct() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .distinct()
        .subscribe(subscriber);
    compositeSubscription.add(subscription);
  }

  private void doOfType() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .ofType(ListItem.class)
        .subscribe(subscriber);
    compositeSubscription.add(subscription);
  }

  private void doElementAt() {
    initSubscriber();
    data.clear();
    Subscription subscription = Observable.from(DataSource.getData())
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .elementAt(15)
        .subscribe(subscriber);
    compositeSubscription.add(subscription);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    compositeSubscription.unsubscribe();
  }
}
