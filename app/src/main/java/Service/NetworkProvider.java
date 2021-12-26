package Service;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapters.SurveyAdapter;
import DTO.SurveyDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkProvider {



    public static List<SurveyDTO> getAllSurveys(String token, Context context){
        final List<SurveyDTO>[] ret = new List[]{new ArrayList<>()};

        Call<List<SurveyDTO>> call1 = NetworkService.getInstance()
                .getJSONApi().getAllSurveys("Bearer_" + token);
        call1.enqueue(new Callback<List<SurveyDTO>>() {
            @Override
            public void onResponse(Call<List<SurveyDTO>> call, Response<List<SurveyDTO>> response) {
                List<SurveyDTO> suerveyListResp = response.body();
                if (suerveyListResp != null) {
                    ret[0] = suerveyListResp;
                }
            }
            @Override
            public void onFailure(Call<List<SurveyDTO>> call, Throwable t) {
                Toast.makeText(context, "onFailure called getAllSurveys", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                call.cancel();
            }

        });

        return ret[0];

    }
}
