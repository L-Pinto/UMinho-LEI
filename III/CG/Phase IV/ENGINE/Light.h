//
// Created by luisa on 16/05/21.
//

#ifndef TRABALHOCG_LIGHT_H
#define TRABALHOCG_LIGHT_H

#include "Point.h"
#include <string>

class Light {
    class LightBuilder;
    LightBuilder* lightBuilder;

public:

    Light();

    Light(std::string type, Point coordinates);



    void setType(std::string type);

    void setCoordenates(Point coordenates);

    std::string getType();

    Point getCoordenates();

    ~Light();

};

#endif //TRABALHOCG_LIGHT_H