#ifndef Primitive_h
#define Primitive_h

#include <vector>
#include "Point.h"
#include "Primitive.h"
#include <string>

class Primitive{
	class PrimitiveBuilder;
	PrimitiveBuilder* primitiveBuilder;

public:

	Primitive();

	int Primitive::getTypePrimitive();

	void Primitive::setType(int t);

	Primitive(std::vector<Point> vertices, int type);

	Primitive(const Primitive& p);

	std::vector<Point> getVertices();

	int getNrVertices();

	Point getPoint(int index);

	void addPoint(Point p);


	~Primitive();
	
};

#endif