package com.purplepath.purplepath.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dinesh on 20/05/16.
 */
public class NavigationDrawerAdapter extends BaseExpandableListAdapter {

 /*   private int mdrawable[] = {R.drawable.ic_my_account_new,
            R.drawable.ic_my_report_new,
            R.drawable.ic_upgrade_new,
            R.drawable.ic_payment_new,
            R.drawable.ic_about_us_new,
            R.drawable.ic_feedback_new,
            R.drawable.ic_terms_conditions_new,
            R.drawable.ic_contact_us_new,
            R.drawable.ic_logout_new

    };*/
 private int mdrawable[] = {R.drawable.ic_my_account_new,
       //  R.drawable.ic_payment_new,
         R.drawable.ic_about_us_new,
         R.drawable.ic_feedback_new,
         R.drawable.ic_terms_conditions_new,
         R.drawable.ic_privacy_policy,
         R.drawable.ic_contact_us_new,
         R.drawable.ic_logout_new

 };
    private int mChilddrawable[] = {R.drawable.ic_personal_details_icon_selat,
            R.drawable.ic_family_details_icon_selat,
            R.drawable.ic_goals_icon_selat,
            R.drawable.ic_income_details_icon,
            R.drawable.ic_expense_details_icon_selat,
            R.drawable.ic_asst_icon_selat,
            R.drawable.ic_liability_icon_selat,
            R.drawable.ic_insurance_selat,
            R.drawable.ic_retirement_icon_selat,
            R.drawable.ic_tax_prepaid,
            R.drawable.ic_logout_new

    };

    private int mChild2drawable[] = {R.drawable.ic_income_chart_selat, R.drawable.ic_expense_chart_selat,
            R.drawable.ic_networth_chart_selat, R.drawable.ic_asset_chart_selat,
            R.drawable.ic_cash_management_chart_selat, R.drawable.ic_emergency_fund_chart_selat,
            R.drawable.ic_goal_timeline_chart_selat, R.drawable.ic_cash_flow_management_chart_selat,
            R.drawable.ic_insurance_chart, R.drawable.ic_tax_cash_flow_chart_selat
            , R.drawable.ic_risk_chart, R.drawable.ic_risk_assenment_result_selat

    };

    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private String childname[] = {"Personal Details","Family Details", "Goal Details",
            "Income Details","Expense Details","Asset Details","Liabilities",
            "Insurance Details","Retirement Benefits","Tax Prepaid" };
    private String child2name[] = {"Income Analysis",
            "Expense Analysis", "Networth ", "Asset Allocation Chart", "Cash Management ", "Emergency fund", " Timeline", "Cash Flow Management", " Insurance Chart"
            , "Tax Cash Flow Chart", "Risk Assesment", "Risk Assesment Result"};

//    private String name[] = {"Profile","Reports", "Upgrade","Payment",
//           "About Us", "Feedback","Terms Conditions","Contact Us","Logout"};

    private String name[] = {"Profile",
               "About Us", "Feedback","Terms and Conditions","Privacy Policy","Contact Us","Logout"};
    private Context mContext;

    public NavigationDrawerAdapter(Context mContext) {
        List empty = new ArrayList<>();
        _listDataChild = new HashMap<>();
        this.mContext = mContext;
        _listDataChild.put("Profile", empty);
//        _listDataChild.put("My Data", new ArrayList<>(Arrays.asList(childname)));
//        _listDataChild.put("My Plan", new ArrayList<>(Arrays.asList(child2name)));
//        _listDataChild.put("Reports", empty);
//        _listDataChild.put("Upgrade", empty);

     //   _listDataChild.put("Payment",empty);
        _listDataChild.put("About Us", empty);
        _listDataChild.put("Feedback", empty);
        _listDataChild.put("Terms Conditions", empty);
        _listDataChild.put("Privacy Policy", empty);
        _listDataChild.put("Contact us", empty);
        _listDataChild.put("Logout", empty);


    }


//    @Override
//    public int getCount() {
//        return name.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mdrawable[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View v = convertView;
//
//        if (convertView == null) { // if it's not recycled, initialize some
//            // attributes
//            LayoutInflater vi = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v = vi.inflate(R.layout.navigation_menu_list_view, null);
//            ImageView menuIcon=(ImageView)v.findViewById(R.id.menu_icon_id);
//            TextView menuName=(TextView)v.findViewById(R.id.menu_name_id);
//            menuIcon.setImageResource(mdrawable[position]);
//            menuName.setText(name[position]);
//
//        }
//        return v;
//    }

    @Override
    public int getGroupCount() {
        return _listDataChild.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (this._listDataChild.get(this.name[i]) != null)
            return this._listDataChild.get(this.name[i])
                    .size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int position) {
        return _listDataChild.get("" + position);
    }

    @Override
    public Object getChild(int i, int i1) {
        if (_listDataChild.get(this.name[i]).get(i1) != null)
            return this._listDataChild.get(this.name[i])
                    .get(i1);
        else
            return 0;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View v = view;
        // if it's not recycled, initialize some
        // attributes
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.navigation_menu_list_view, null);
        ImageView menuIcon = v.findViewById(R.id.menu_icon_id);
        TextView menuName = v.findViewById(R.id.menu_name_id);
        menuIcon.setImageResource(mdrawable[i]);
        menuName.setText(name[i]);


        return v;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View v = view;
        // if it's not recycled, initialize some
        // attributes
        LayoutInflater vi = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.navigation_child_view, null);

        ImageView menuIcon = v.findViewById(R.id.menu_icon_id);
        TextView menuName = v.findViewById(R.id.menu_name_id);

        if (i == 1) {
            menuIcon.setImageResource(mChilddrawable[i1]);
            menuName.setText(childname[i1]);
        } else if (i == 2) {
            menuIcon.setImageResource(mChild2drawable[i1]);
            menuName.setText(child2name[i1]);

        }


        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
