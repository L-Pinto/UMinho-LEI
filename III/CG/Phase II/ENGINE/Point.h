
#ifndef Point_h
#define Point_h


class Point {
	class PointBuilder;
	PointBuilder* pointBuilder;

public:


	Point();


	Point(float x, float y, float z);


	Point(const Point& v);


	float getX();


	float getY();


	float getZ();


	void setX(float x);


	void setY(float y);


	void setZ(float z);


	~Point();
};

#endif 
