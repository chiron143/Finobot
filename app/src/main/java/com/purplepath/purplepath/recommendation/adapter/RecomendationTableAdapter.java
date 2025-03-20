package com.purplepath.purplepath.recommendation.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.finobot.finobot.R;
import com.purplepath.purplepath.taxproduct.holder.CellViewHolder;
import com.purplepath.purplepath.taxproduct.holder.ColumnHeaderViewHolder;
import com.purplepath.purplepath.taxproduct.holder.RowHeaderViewHolder;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

/**
 * Created by dinesh on 26/02/18.
 */

public class RecomendationTableAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    private Context mContext;


    public RecomendationTableAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


    //Cell values
    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout;
        switch (viewType) {
            default:
                layout = LayoutInflater.from(mContext).inflate(R.layout.recomed_table_view_cell__layout,parent, false);
                return new CellViewHolder(layout);
        }
    }
    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;
        CellViewHolder viewHolder = (CellViewHolder) holder;

        viewHolder.setData(cell.getData());
    }



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
        // Get Corner xml layout
        View corner = LayoutInflater.from(mContext).inflate(R.layout.table_view_corner_layout, null);
       /* corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortState sortState = AmortizationTableAdapter.this.getTableView().getRowHeaderSortingStatus();
                if(sortState != SortState.ASCENDING) {
                    Log.d("TableViewAdapter", "Order Ascending");
                    AmortizationTableAdapter.this.getTableView().sortRowHeader(SortState.ASCENDING);
                } else {
                    Log.d("TableViewAdapter", "Order Descending");
                    AmortizationTableAdapter.this.getTableView().sortRowHeader(SortState.DESCENDING);
                }
            }
        });*/
        return corner;
    }


    @Override
    public int getColumnHeaderItemViewType(int position) {

        return position;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {

        return position;
    }

    @Override
    public int getCellItemViewType(int column) {

        switch (column) {
            default:
                // Default view type
                return column;
        }
    }
}