#ifndef Primitive_h
#define Primitive_h

#include <vector>
#include "Point.h"
#include "Primitive.h"

class Primitive{
	class PrimitiveBuilder;
	PrimitiveBuilder* primitiveBuilder;

public:

	Primitive();

	Primitive(std::vector<Point> vertices);

	Primitive(const Primitive& p);

	std::vector<Point> getVertices();

	int getNrVertices();

	Point getPoint(int index);

	void addPoint(Point p);

	~Primitive();
	
};

#endif