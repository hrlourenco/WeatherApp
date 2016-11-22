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
import mei.weatherapp.Utils;
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
    return 0;// ds.get(i).getId();
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    ImageView img_tempo;
    TextView tv_tempMax;
    TextView tv_tempMin;
    TextView tv_data;
    TextView tv_vento;
    TextView tv_previsao;

    if(view == null){
      LayoutInflater infl = LayoutInflater.from(this.ctx);
      view = infl.inflate(R.layout.forecast_line, null);
    }

    img_tempo = (ImageView) view.findViewById(R.id.img_tempo);
    tv_tempMax = (TextView) view.findViewById(R.id.tv_tempMax);
    tv_tempMin = (TextView) view.findViewById(R.id.tv_tempMin);
    tv_data = (TextView) view.findViewById(R.id.tv_data);
    tv_vento = (TextView) view.findViewById(R.id.tv_vento);
    tv_previsao = (TextView) view.findViewById(R.id.tv_previsao);

    Condicoes aux = (Condicoes) this.getItem(i);
/*
    int id = ctx.getResources().getIdentifier(Utils.MakeAWImageString(aux.getWeatherIcon()),"drawable", ctx.getPackageName());
    img_tempo.setImageResource(id);
    tv_tempMax.setText(aux.getTemperatureMax() + "º C");
    tv_tempMin.setText(aux.getTemperatureMin() + "º C");
    tv_data.setText(aux.getData());
    tv_vento.setText(aux.getWindSpeed() + " Kh");
    tv_previsao.setText(aux.getWeatherText());
*/
    //lixo.setText(id);

    return view;
  }
}
