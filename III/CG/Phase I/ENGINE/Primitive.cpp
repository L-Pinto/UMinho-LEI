#include <iterator>

#include "Primitive.h"



class Primitive::PrimitiveBuilder {

	//variável privada que guarda os vértices
private:
	std::vector<Point> vertices;

public:
	PrimitiveBuilder() = default;

	PrimitiveBuilder(std::vector<Point> vertices) {
		for (size_t i = 0; i < vertices.size(); i++) {
			this->vertices.push_back(vertices.at(i));
		}
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

Primitive::Primitive(std::vector<Point> vertices) : primitiveBuilder{ new PrimitiveBuilder(vertices) } {}

Primitive::Primitive(const Primitive& p) : primitiveBuilder{ new PrimitiveBuilder(p.primitiveBuilder->getVertices()) } {}

std::vector<Point> Primitive::getVertices() {
	return primitiveBuilder->getVertices();
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