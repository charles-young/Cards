package design.cyoung;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Card> mCards;
    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        TextView displayTitle;
        TextView displaySubtitle;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            displayTitle = (TextView) itemView.findViewById(R.id.card_title);
            displaySubtitle = (TextView) itemView.findViewById(R.id.card_subtitle);
        }
    }

    CardAdapter(List<Card> cards) {
        this.mCards = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.displayTitle.setText(mCards.get(i).getDisplayTitle());
        holder.displaySubtitle.setText(mCards.get(i).getDisplaySubtitle());
        //holder.displayTitle.setText(cards.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
