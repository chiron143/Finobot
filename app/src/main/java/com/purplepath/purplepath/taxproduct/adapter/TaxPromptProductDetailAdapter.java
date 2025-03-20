package com.purplepath.purplepath.taxproduct.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.taxproduct.holder.CellViewHolder;
import com.purplepath.purplepath.taxproduct.holder.ColumnHeaderViewHolder;
import com.purplepath.purplepath.taxproduct.holder.RowHeaderViewHolder;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.Ded_by_prods;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by pravinr on 2/14/18.
 */

public class TaxPromptProductDetailAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    private Context mContext;

    ArrayList< ArrayList<Ded_by_prods>> mfilterarrayAvailed = new ArrayList<>();

    private TaxPromptNewModel taxAnalysistModel;

    private BigInteger availed=BigInteger.ZERO;

    private BigInteger total_sub_all=BigInteger.ZERO;

    BigInteger entitle=BigInteger.ZERO;

    BigInteger entitle_greater=BigInteger.ZERO;

    public TaxPromptProductDetailAdapter(Context mContext, ArrayList<ArrayList<Ded_by_prods>> mfilterarrayAvailed,
                                         TaxPromptNewModel taxAnalysistModel) {
        super(mContext);

        this.mContext = mContext;
        this.mfilterarrayAvailed=mfilterarrayAvailed;
        this.taxAnalysistModel=taxAnalysistModel;

    }


    //Cell values
    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout;
        switch (viewType) {
            default:
                layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_cell_layout,parent, false);
                return new CellViewHolder(layout);
        }
    }
    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;
            CellViewHolder viewHolder = (CellViewHolder) holder;

            viewHolder.setData(cell.getData());

     //   cellValues(mfilterarrayAvailed);

        int length=mfilterarrayAvailed.size();
        for(int i=0;i<length;i++){

            int innerlength=mfilterarrayAvailed.get(i).size();

            String  section = "",instrument="",str_entitle = "",str_availed="",
                    str_pending = "",str_availed_greater="";

            availed=BigInteger.ZERO;
            entitle=BigInteger.ZERO;
            total_sub_all=BigInteger.ZERO;
            entitle_greater=BigInteger.ZERO;

            int checkLess;
            for(int j=0;j<innerlength;j++) {

                try {

                    if (mfilterarrayAvailed.get(i).get(j).getTax_section() != null) {
                        if (j == 0) {
                            section = mfilterarrayAvailed.get(i).get(j).getTax_section();
                        }
                    }
                    //instrument
                    if (mfilterarrayAvailed.get(i).get(j).getProd_name() != null) {
                        instrument = instrument + mfilterarrayAvailed.get(i).get(j).getProd_name() + " <br/> ";
                    }

                    //entitle
                    if (mfilterarrayAvailed.get(i).get(j).getEntitled() != null) {
                        entitle = new BigInteger(mfilterarrayAvailed.get(i).get(j).getEntitled());
                        str_entitle = String.valueOf(UtileKit.formatedNumbers(entitle));
                    } else {
                        str_entitle = "0";
                    }
                    //availed
                    if (mfilterarrayAvailed.get(i).get(j).getAllowed_value() != null &&
                            mfilterarrayAvailed.get(i).get(j).getEntitled()!=null) {
                        availed = availed.add(new BigInteger(mfilterarrayAvailed.get(i).get(j).getAllowed_value()));

                        str_availed = String.valueOf(UtileKit.formatedNumbers(availed));

                        entitle_greater=new BigInteger(mfilterarrayAvailed.get(i).get(j).getEntitled());

                        str_availed_greater=String.valueOf(UtileKit.formatedNumbers(entitle_greater));

                    } else {
                        str_availed = "0";
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

                 

    }

   /* private void cellValues(ArrayList<ArrayList<Ded_by_prods>> mfilterarrayAvailed) {

        int length=mfilterarrayAvailed.size();

        for(int i=0;i<length;i++){

            int innerlength=mfilterarrayAvailed.get(i).size();

            String  section = "",instrument="",str_entitle = "",str_availed="",
                    str_pending = "",str_availed_greater="";

            availed=BigInteger.ZERO;
            entitle=BigInteger.ZERO;
            total_sub_all=BigInteger.ZERO;
            entitle_greater=BigInteger.ZERO;

            int checkLess;
               for(int j=0;j<innerlength;j++) {

                   try {

                       if (mfilterarrayAvailed.get(i).get(j).getTax_section() != null) {
                           if (j == 0) {
                               section = mfilterarrayAvailed.get(i).get(j).getTax_section();
                           }
                       }
                       //instrument
                       if (mfilterarrayAvailed.get(i).get(j).getProd_name() != null) {
                           instrument = instrument + mfilterarrayAvailed.get(i).get(j).getProd_name() + " <br/> ";
                       }

                       //entitle
                       if (mfilterarrayAvailed.get(i).get(j).getEntitled() != null) {
                           entitle = new BigInteger(mfilterarrayAvailed.get(i).get(j).getEntitled());
                           str_entitle = String.valueOf(UtileKit.formatedNumbers(entitle));
                       } else {
                           str_entitle = "0";
                       }
                       //availed
                       if (mfilterarrayAvailed.get(i).get(j).getAllowed_value() != null &&
                               mfilterarrayAvailed.get(i).get(j).getEntitled()!=null) {
                           availed = availed.add(new BigInteger(mfilterarrayAvailed.get(i).get(j).getAllowed_value()));

                           str_availed = String.valueOf(UtileKit.formatedNumbers(availed));

                           entitle_greater=new BigInteger(mfilterarrayAvailed.get(i).get(j).getEntitled());

                           str_availed_greater=String.valueOf(UtileKit.formatedNumbers(entitle_greater));

                       } else {
                           str_availed = "0";
                       }

                   }catch (Exception e){
                       e.printStackTrace();
                   }
            }
        }
    }*/


    //Column header
    @Override
    public RecyclerView.ViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_column_header_layout, parent, false);
        return new ColumnHeaderViewHolder(layout, getTableView());
    }@Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object
            columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeader);
    }






//Row header
    @Override
    public RecyclerView.ViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_row_header_layout, parent, false);
        return new RowHeaderViewHolder(layout);
    }
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel,int rowPosition) {
        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeader.getData()));
    }






    @Override
    public View onCreateCornerView() {
    return null;
    }


    @Override
    public int getColumnHeaderItemViewType(int position) {

        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {

        return 0;
    }

    @Override
    public int getCellItemViewType(int column) {

        switch (column) {
            default:
                // Default view type
                return 0;
        }
    }
}
