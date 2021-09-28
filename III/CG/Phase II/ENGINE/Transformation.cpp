
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

public:
    TransformationBuilder() = default;

    TransformationBuilder(string name, float x, float y, float z, float angle) {
        this->name = name;
        this->x = x;
        this->y = y;
        this->z = z;
        this->angle = angle;

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


    ~TransformationBuilder() = default;
};

Transformation::Transformation() : transformationBuilder{ new class TransformationBuilder() } {}

Transformation::Transformation(string name, float x, float y, float z, float angle) : transformationBuilder{ new TransformationBuilder(name, x, y, z, angle) } {}



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

/*
Transformation::~Transformation() {
    if (transformationBuilder != nullptr) {
        delete transformationBuilder;
        transformationBuilder = nullptr;
    }
} */


