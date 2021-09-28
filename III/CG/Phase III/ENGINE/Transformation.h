#ifndef Transformation_h
#define Transformation_h

#include <string>
#include "Transformation.h"

class Transformation{
    class TransformationBuilder;
    TransformationBuilder* transformationBuilder;

public:

    Transformation();

    Transformation(std::string name, float x, float y, float z, float angle, float time);

    std::string getName();

    float getX();

    float getY();

    float getZ();

    float getAngle();

    float getTime();


    /*
    void setX();

    void setY();

    void setZ();

    void setAngle();
     */

    //~Transformation();

};

#endif