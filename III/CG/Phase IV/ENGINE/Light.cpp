//
// Created by luisa on 16/05/21.
//

#include <iterator>
#include <GL/glut.h>

#include "Light.h"
#include "Point.h"

class Light::LightBuilder {

    private:
        std::string type;
        Point coordenates;

    public:
        LightBuilder() = default;

        LightBuilder(std::string type, Point coordinates) {
            this->type = type;
            this->coordenates = coordinates;
        }

        

        void setType(std::string type) {
            this->type = type;
        }

        void setCoordenates(Point c) {
            coordenates.setX(c.getX());
            coordenates.setY(c.getY());
            coordenates.setZ(c.getZ());
        }

        std::string getType(){
            return type;
        }

        Point getCoordenates(){
            return coordenates;
        }

        ~LightBuilder() = default;
};

Light::Light() : lightBuilder{ new class LightBuilder()} {}

void Light::setType(std::string type) {
    lightBuilder->setType(type);
}

void Light::setCoordenates(Point coordenates) {
    lightBuilder->setCoordenates(coordenates);
}

std::string Light::getType() {
    return lightBuilder->getType();
}

Point Light::getCoordenates() {
    return lightBuilder->getCoordenates();
}

Light::~Light() {
    /*
    if (lightBuilder != nullptr) {
        delete lightBuilder;
        lightBuilder = nullptr;
    }
     */
}




