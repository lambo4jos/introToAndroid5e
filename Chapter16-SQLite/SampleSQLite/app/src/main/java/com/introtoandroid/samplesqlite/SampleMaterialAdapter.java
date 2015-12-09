package com.introtoandroid.samplesqlite;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SampleMaterialAdapter extends RecyclerView.Adapter<SampleMaterialAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "SampleMaterialAdapter";

    private Context context;
    private ArrayList<Card> cardsList;
    public CardsData cardsData;

    public SampleMaterialAdapter(Context context, ArrayList<Card> cardsList, CardsData cardsData) {
        this.context = context;
        this.cardsList = cardsList;
        this.cardsData = cardsData;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String name = cardsList.get(position).getName();
        int color = cardsList.get(position).getColorResource();
        TextView initial = viewHolder.initial;
        TextView nameTextView = viewHolder.name;
        nameTextView.setText(name);
        initial.setBackgroundColor(color);
        initial.setText(Character.toString(name.charAt(0)));
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        animateCircularReveal(viewHolder.itemView);
    }

    public void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    public void animateCircularDelete(final View view, final int list_position) {
        int centerX = view.getWidth();
        int centerY = view.getHeight();
        int startRadius = view.getWidth();
        int endRadius = 0;
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit adapter position " + list_position);
                Log.d(DEBUG_TAG, "SampleMaterialAdapter onAnimationEnd for Edit cardId " + getItemId(list_position));

                view.setVisibility(View.INVISIBLE);
                Card card = new Card();
                card.setId(getItemId(list_position));
                card.setListPosition(list_position);
                new DeleteCardTask().execute(card);
            }
        });
        animation.start();
    }

    public void addCard(String name, int color) {
        Card card = new Card();
        card.setName(name);
        card.setColorResource(color);
        new CreateCardTask().execute(card);
    }

    public void updateCard(String name, int list_position) {
        Card card = new Card();
        card.setName(name);
        card.setId(getItemId(list_position));
        card.setListPosition(list_position);
        new UpdateCardTask().execute(card);
    }

    public void deleteCard(View view, int list_position) {
        animateCircularDelete(view, list_position);
    }

    @Override
    public int getItemCount() {
        if (cardsList.isEmpty()) {
            return 0;
        } else {
            return cardsList.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return cardsList.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.card_view_holder, viewGroup, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView initial;
        private TextView name;
        private Button deleteButton;

        public ViewHolder(View v) {
            super(v);
            initial = (TextView) v.findViewById(R.id.initial);
            name = (TextView) v.findViewById(R.id.name);
            deleteButton = (Button) v.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateCircularDelete(itemView, getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pair<View, String> p1 = Pair.create((View) initial, SampleMaterialActivity.TRANSITION_INITIAL);
                    Pair<View, String> p2 = Pair.create((View) name, SampleMaterialActivity.TRANSITION_NAME);
                    Pair<View, String> p3 = Pair.create((View) deleteButton, SampleMaterialActivity.TRANSITION_DELETE_BUTTON);

                    ActivityOptionsCompat options;
                    AppCompatActivity act = (AppCompatActivity) context;
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, p1, p2, p3);

                    int requestCode = getAdapterPosition();
                    String name = cardsList.get(requestCode).getName();
                    int color = cardsList.get(requestCode).getColorResource();

                    Log.d(DEBUG_TAG, "SampleMaterialAdapter itemView listener for Edit adapter position " + requestCode);

                    Intent transitionIntent = new Intent(context, TransitionEditActivity.class);
                    transitionIntent.putExtra(SampleMaterialActivity.EXTRA_NAME, name);
                    transitionIntent.putExtra(SampleMaterialActivity.EXTRA_INITIAL, Character.toString(name.charAt(0)));
                    transitionIntent.putExtra(SampleMaterialActivity.EXTRA_COLOR, color);
                    transitionIntent.putExtra(SampleMaterialActivity.EXTRA_UPDATE, false);
                    transitionIntent.putExtra(SampleMaterialActivity.EXTRA_DELETE, false);
                    ((AppCompatActivity) context).startActivityForResult(transitionIntent, requestCode, options.toBundle());
                }
            });
        }
    }

    private class CreateCardTask extends AsyncTask<Card, Void, Card> {
        @Override
        protected Card doInBackground(Card... cards) {
            cardsData.create(cards[0]);
            cardsList.add(cards[0]);
            return cards[0];
        }

        @Override
        protected void onPostExecute(Card card) {
            super.onPostExecute(card);
            ((SampleMaterialActivity) context).doSmoothScroll(getItemCount() - 1);
            notifyItemInserted(getItemCount());
            Log.d(DEBUG_TAG, "Card created with id " + card.getId() + ", name " + card.getName() + ", color " + card.getColorResource());
        }
    }

    private class UpdateCardTask extends AsyncTask<Card, Void, Card> {
        @Override
        protected Card doInBackground(Card... cards) {
            cardsData.update(cards[0].getId(), cards[0].getName());
            cardsList.get(cards[0].getListPosition()).setName(cards[0].getName());
            return cards[0];
        }

        @Override
        protected void onPostExecute(Card card) {
            super.onPostExecute(card);
            Log.d(DEBUG_TAG, "list_position is " + card.getListPosition());
            notifyItemChanged(card.getListPosition());
        }
    }

    private class DeleteCardTask extends AsyncTask<Card, Void, Card> {
        @Override
        protected Card doInBackground(Card... cards) {
            cardsData.delete(cards[0].getId());
            cardsList.remove(cards[0].getListPosition());
            return cards[0];
        }

        @Override
        protected void onPostExecute(Card card) {
            super.onPostExecute(card);
            notifyItemRemoved(card.getListPosition());
        }
    }
}