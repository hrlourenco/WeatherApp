package mei.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mei.weatherapp.R;
import mei.weatherapp.contratos.Condicoes;

/**
 * Created by joaofaria on 13/09/16.
 */
public class forecastAdapter extends BaseAdapter {
  private Context ctx;
  private List<Condicoes> ds;

  public forecastAdapter(Context ctx, List<Condicoes> ds) {
    this.ctx = ctx;
    this.ds = ds;
  }

  @Override
  public int getCount() {
    return ds.size();
  }

  @Override
  public Object getItem(int i) {
    return ds.get(i);
  }

  @Override
  public long getItemId(int i) {
    return ds.get(i).getId();
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    ImageView img_tempo;

    if(view == null){
      LayoutInflater infl = LayoutInflater.from(this.ctx);
      view = infl.inflate(R.layout.forecast_line, null);
    }

    img_tempo = (ImageView) view.findViewById(R.id.img_tempo);

    Condicoes aux = (Condicoes) this.getItem(i);

    String weatherIcon = "aw" + aux.getWeatherIcon() + "s.png";
    //img_tempo.setImageBitmap();

    return view;
  }
}
