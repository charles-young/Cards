package design.cyoung;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    static private List<Card> mCards;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cv;
        TextView displayTitle;
        TextView displaySubtitle;

        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            displayTitle = itemView.findViewById(R.id.card_title);
            displaySubtitle = itemView.findViewById(R.id.card_subtitle);
        }

        @Override
        public void onClick(View view) {
            Card card = mCards.get(getLayoutPosition());

            ImageView qrCode = new ImageView(view.getContext());
            String text = card.getDisplayTitle(); // Whatever you need to encode in the QR code
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text, card.getBarcodeFormat(), 750, 750);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qrCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(view.getContext()).
                            setMessage("Message above the image").
                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).
                            setView(qrCode);
            builder.create().show();
        }
    }

    CardAdapter(List<Card> cards, Context context) {
        mCards = cards;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.displayTitle.setText(mCards.get(i).getDisplayTitle());
        holder.displaySubtitle.setText(mCards.get(i).getDisplaySubtitle());
        //holder.displayTitle.setText(cards.get(i).getTitle());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = mCards.get(i);

                ImageView qrCode = new ImageView(context);
                String text = card.getBarcodeText(); // Whatever you need to encode in the QR code
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, card.getBarcodeFormat(), 750, 750);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(context).
                                setMessage(mCards.get(i).getDisplayTitle()).
                                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).
                                setView(qrCode);
                builder.create().show();
            }
        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Would you like to remove this card?")
                        .setPositiveButton("Yes, I don't need it anymore.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCards.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, mCards.size());
                                //Save scanned card to list
                                Gson gson = new Gson();
                                SharedPreferences mPrefs = context.getSharedPreferences("CardStore", Context.MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                                ArrayList<Card> cards = gson.fromJson(mPrefs.getString("CardList", gson.toJson(new ArrayList<Card>())), new TypeToken<List<Card>>(){}.getType());

                                cards.remove(i);

                                prefsEditor.putString("CardList", gson.toJson(cards));
                                prefsEditor.apply();
                            }
                        })
                        .setNegativeButton("Whoops... Take me back!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
