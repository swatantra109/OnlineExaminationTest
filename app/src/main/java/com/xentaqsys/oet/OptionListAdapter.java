package com.xentaqsys.oet;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Swatantra.Singh on 9/21/17.
 */

public class OptionListAdapter extends RecyclerView.Adapter<OptionListAdapter.ViewHolder> {

    private ArrayList<OptionItems> values;
    Context c;
    private int lastCheckedPosition = -1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public Button txtHeader;
        public TextView optionText;
        public TextView optionTextView;

        public View layout;
        public RelativeLayout linear;

        public ViewHolder(View v) {
            super(v);
            layout = v;
//            txtHeader = (Button) v.findViewById(R.id.superiio);
            optionText = (TextView) v.findViewById(R.id.textViewOption);
            optionTextView = (TextView) v.findViewById(R.id.optionTextView);

            linear = (RelativeLayout) v.findViewById(R.id.linear);

//            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }


    }

//    public void add(int position, String item) {
//        values.add(position, item);
//        notifyItemInserted(position);
//    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OptionListAdapter(Context context, ArrayList<OptionItems> myDataset) {
        values = myDataset;
        c = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.question_option_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        final String name = values.get(position).getProdFlag();
//
//
//        final String productFlag = String.valueOf(values.get(position).getProdSeq());
//        final String productSeq = String.valueOf(values.get(position).getProdSeq());
//        final String productColorStreamId = values.get(position).getProdColoStreamId();
//        final String PanelItemsID = values.get(position).getPanelItemId();
//        final String coatTypes = values.get(position).getCoatType();
//        final String oem_name1 = values.get(position).getOem();
//        final String Intt_colour_code1 = values.get(position).getC_Code();
//        final String bar_code = values.get(position).getBarCode();

//        holder.txtHeader.setText(values.get(position).getValue1());
        holder.optionText.setText(values.get(position).getValue3());

        holder.optionTextView.setText(values.get(position).getValue1());
     if (position==lastCheckedPosition){
//         holder.card_view.setCardBackgroundColor(Color.GREEN);
//         holder.optionTextView.setBackgroundResource(R.drawable.round_button_selected);
         holder.linear.setBackgroundColor(Color.parseColor("#05b121"));
     }
     else {
//         holder.card_view.setCardBackgroundColor(null);
         holder.linear.setBackgroundColor(Color.parseColor("#ff6600"));

     }
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(c, "Show Instrucitons for test" , Toast.LENGTH_SHORT).show();
//                Intent intt = new Intent(c, TestListActivity.class);
//                intt.putExtra("CourseName", values.get(position).getValue1());
//                intt.putExtra("CourseName", values.get(position).getValue2());

//                c.startActivity(intt);
//                FragmentManager manager = ((AppCompatActivity) c).getSupportFragmentManager();
////
//                InstructionDialog eventRegFragment= new InstructionDialog();
////
//                eventRegFragment.show(manager, "Edit Fragment");

//                FragmentTransaction ft = ((FragmentActivity)c).getSupportFragmentManager().beginTransaction();
//                InstructionDialog dialog = InstructionDialog();
//                dialog.show(ft, "Tag");
//                InstructionDialog newFragment = new InstructionDialog();
////                newFragment.show(getSupportFragmentManager(), "tag");
//
//                FragmentManager fr = ((AppCompatActivity)c).getFragmentManager();
//                InstructionDialog msgDialog = new InstructionDialog();
//                msgDialog.show(fr, "Dialog");

                lastCheckedPosition =position;
                notifyDataSetChanged();




            }
        });


//        holder.txtFooter.setText("Footer: " + name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}

