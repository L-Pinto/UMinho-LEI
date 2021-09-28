#ifndef Transformation_h
#define Transformation_h

#include <string>
#include "Transformation.h"

class Transformation{
    class TransformationBuilder;
    TransformationBuilder* transformationBuilder;

public:

    Transformation();

    Transformation(std::string name, float x, float y, float z, float angle);

    std::string getName();

    float getX();

    float getY();

    float getZ();

    float getAngle();


    /*
    void setX();

    void setY();

    void setZ();

    void setAngle();
     */

    //~Transformation();

};

#endif