#include <iterator>
#include <string>

#include "Primitive.h"



class Primitive::PrimitiveBuilder {

	//variável privada que guarda os vértices
private:
	std::vector<Point> vertices;
	int type;

public:
	PrimitiveBuilder() = default;

	PrimitiveBuilder(std::vector<Point> vertices, int type) {
		for (size_t i = 0; i < vertices.size(); i++) {
			this->vertices.push_back(vertices.at(i));
		}

		this->type = type;
		
	}

	int getTypePrimitive() {
		return type;
	}


	void setType(int t) {
		this->type = t;
	}

	std::vector<Point> getVertices() {
		return vertices;
	}

	int getNrVertices() {
		return vertices.size();
	}

	Point getPoint(int index) {
		return vertices[index];
	}

	void addPoint(Point p) {
		vertices.push_back(p);
	}

	~PrimitiveBuilder() = default;
};

Primitive::Primitive() : primitiveBuilder{ new class PrimitiveBuilder() } {}

Primitive::Primitive(std::vector<Point> vertices, int type) : primitiveBuilder{ new PrimitiveBuilder(vertices, type) } {}

Primitive::Primitive(const Primitive& p) : primitiveBuilder{ new PrimitiveBuilder(p.primitiveBuilder->getVertices(), p.primitiveBuilder->getTypePrimitive()) } {}

std::vector<Point> Primitive::getVertices() {
	return primitiveBuilder->getVertices();
}

int Primitive::getTypePrimitive() {
	return primitiveBuilder->getTypePrimitive();
}

void Primitive::setType(int t) {
	return primitiveBuilder->setType(t);
}


void Primitive::addPoint(Point p) {
	primitiveBuilder->addPoint(p);
}

int Primitive::getNrVertices() {
	return primitiveBuilder->getNrVertices();
}

Point Primitive::getPoint(int index) {
	return primitiveBuilder->getPoint(index);
}

Primitive::~Primitive() {
	if (primitiveBuilder != nullptr) {
		delete primitiveBuilder;
		primitiveBuilder = nullptr;
	}
}