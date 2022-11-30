package id.ac.unsyiah.android.selfreminderv2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InsertAdapter extends RecyclerView.Adapter<InsertAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<ViewConGetSet> viewConGetSetArrayList;
    private Context context;

    // constructor
    public InsertAdapter(ArrayList<ViewConGetSet> viewConGetSetArrayList, Context context) {
        this.viewConGetSetArrayList = viewConGetSetArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jadwal_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        final ViewConGetSet modal = viewConGetSetArrayList.get(position);
        holder.nameTV.setText(modal.getName());
        holder.timeTV.setText(modal.getTime());
        int ic = holder.itemView.getResources().getIdentifier(modal.getIcon(), "drawable", context.getPackageName());
        holder.iconIV.setImageResource(ic);

        Log.d("onBindViewHolder :", String.valueOf(ic));

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure to delete "+modal.getName()+" reminder?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbHelper = new DBHelper(context);
                        int result = dbHelper.delete(modal.getId());
                        if (result > 0){
                            Toast.makeText(context, "Item has been deleted", Toast.LENGTH_SHORT).show();
                            viewConGetSetArrayList.remove(modal);
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();




            }
        });


    }



    @Override
    public int getItemCount() {
        // returning the size of our array list
        return (viewConGetSetArrayList != null) ? viewConGetSetArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView nameTV, timeTV;
        private ImageView iconIV;
        private ImageButton del;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            nameTV = itemView.findViewById(R.id.tv1_c);
            timeTV = itemView.findViewById(R.id.tv2_c);
            iconIV = itemView.findViewById(R.id.iv_c);
            del = itemView.findViewById(R.id.imageButton);

        }
    }
}