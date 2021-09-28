
#include <iterator>
#include <string>

#include "Transformation.h"
using namespace std;

class Transformation::TransformationBuilder {

private:
    string name;
    float x;
    float y;
    float z;
    float angle;
    float time;

public:
    TransformationBuilder() = default;

    TransformationBuilder(string name, float x, float y, float z, float angle, float time) {
        this->name = name;
        this->x = x;
        this->y = y;
        this->z = z;
        this->angle = angle;
        this->time = time; 
    }

    std::string getName() {
        return name;
    }

    float getX(){
        return x;
    }

    float getY(){
        return y;
    }

    float getZ() {
        return z;
    }

    float getAngle() {
        return angle;
    }

    float getTime() {
        return time;
    }


    ~TransformationBuilder() = default;
};

Transformation::Transformation() : transformationBuilder{ new class TransformationBuilder() } {}

Transformation::Transformation(string name, float x, float y, float z, float angle, float time) : transformationBuilder{ new TransformationBuilder(name, x, y, z, angle, time) } {}



std::string Transformation::getName() {
    return transformationBuilder->getName();
}

float Transformation::getX() {
    return transformationBuilder->getX();
}

float Transformation::getY() {
    return transformationBuilder->getY();
}

float Transformation::getZ() {
    return transformationBuilder->getZ();
}

float Transformation::getAngle() {
    return transformationBuilder->getAngle();
}

float Transformation::getTime() {
    return transformationBuilder->getTime();
}

Transformation::~Transformation() {
    /*
    if (transformationBuilder != nullptr) {
        delete transformationBuilder;
        //transformationBuilder = nullptr;
    }
     */
}


