package martinlutern.triviagame.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import martinlutern.triviagame.R;
import martinlutern.triviagame.util.api.model.CategoryResponseModel;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public class CategoryAdapter extends BaseAdapter {
    private final String TAG = getClass().getName();
    private Context mContext;
    private CategoryResponseModel categoryResponseModel;

    public CategoryAdapter(Context mContext, CategoryResponseModel categoryResponseModel) {
        this.mContext = mContext;
        this.categoryResponseModel = categoryResponseModel;
    }

    @Override
    public int getCount() {
        return categoryResponseModel.getTriviaCategories().size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CategoryResponseModel.TriviaCategory triviaCategory = categoryResponseModel.getTriviaCategories().get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_category, null);
        }

        final ImageView bgItem = (ImageView) convertView.findViewById(R.id.bg_item_category);
        final ImageView iconItem = (ImageView) convertView.findViewById(R.id.icon_item_category);
        final TextView txtItem = (TextView) convertView.findViewById(R.id.txt_item_category);

        bgItem.setImageResource(R.drawable.mask);
        iconItem.setImageResource(R.drawable.combined_shape);
        txtItem.setText(triviaCategory.getName());

        return convertView;
    }
}
