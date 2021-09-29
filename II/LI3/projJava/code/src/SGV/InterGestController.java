package SGV;

import SGV.Model.ProdQnt;

import java.util.Map;
import java.util.Set;

public interface InterGestController
{
    void start();
    void setView(InterGestView view);
    void setModel(InterGestModel model);
    void Q10_buildNavMVC(Set<ProdQnt> clientes, int i);
    void buildNavMVC(Set<String> produtos);
    void starEstatistic();
    void Q11_buildNavMVC(Map<String,Double> s, int i);

}
