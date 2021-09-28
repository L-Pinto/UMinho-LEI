#include "Point.h"

#include <cstddef>


class Point::PointBuilder {
    float x;
    float y;
    float z;

public:

    PointBuilder() {
        x = 0;
        y = 0;
        z = 0;
    }


    PointBuilder(float x, float y, float z) {
        this->x = x;
        this->y = y;
        this->z = z;
    }


    float getX() {
        return x;
    }


    float getY() {
        return y;
    }


    float getZ() {
        return z;
    }


    void setX(float x) {
        this->x = x;
    }


    void setY(float y) {
        this->y = y;
    }


    void setZ(float z) {
        this->z = z;
    }


    ~PointBuilder() = default;
};


Point::Point() : pointBuilder{ new PointBuilder() } {}


Point::Point(float x, float y, float z) : pointBuilder{ new PointBuilder(x, y, z) } {}


Point::Point(const Point& v) {
    pointBuilder = new PointBuilder(v.pointBuilder->getX(), v.pointBuilder->getY(), v.pointBuilder->getZ());
}


float Point::getX() {
    return pointBuilder->getX();
}


float Point::getY() {
    return pointBuilder->getY();
}


float Point::getZ() {
    return pointBuilder->getZ();
}


void Point::setX(float x) {
    pointBuilder->setX(x);
}


void Point::setY(float y) {
    pointBuilder->setY(y);
}


void Point::setZ(float z) {
    pointBuilder->setZ(z);
}



