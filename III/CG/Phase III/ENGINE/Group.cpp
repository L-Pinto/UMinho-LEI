#include <iterator>
#include <GL/glut.h>

#include "Group.h"
class Group::GroupBuilder {

private:
	std::vector<Primitive> primitives;
	std::vector<Group> groups;
	std::vector<Transformation> transformations;
	//pontos para formar a curva de catmull
	std::vector<Point> points;
	float angle;

public:
	GroupBuilder() = default;

	GroupBuilder(std::vector<Primitive> primitives, std::vector<Group> groups, std::vector<Transformation> transformations, std::vector<Point> points) {
		for (size_t i = 0; i < primitives.size(); i++) {
			this->primitives.push_back(primitives.at(i));
		}
		for (size_t i = 0; i < groups.size(); i++) {
			this->groups.push_back(groups.at(i));
		}
		for (size_t i = 0; i < transformations.size(); i++) {
			this->transformations.push_back(transformations.at(i));
		}
		for (size_t i = 0; i < points.size(); i++) {
			this->points.push_back(points.at(i));
		}
		angle = 0;
	}


	void setAngle(float angle) {
		this->angle = angle;
	}

	float getAngle() {
		return angle;
	}


	std::vector<Primitive> getPrimitivas() {
		return primitives;
	}

	std::vector<Transformation> getTransformations() {
		return transformations;
	}


	int getNrPrimitives() {
		return primitives.size();
	}

	Transformation getTransformation(int indice){
        return transformations[indice];
	}

    Primitive getPrimitives(int index){
        return primitives[index];
    }


    std::vector<Group> getGroups(){
        return groups;
    }

	std::vector<Point> getPoints() {
		return points;
	}

    Group getGroup(int index){
        return groups[index];
    }

	int getNrGroups() {
		return groups.size();
	}

	int getNrTransformations() {
		return transformations.size();
	}

	void addTransformation(Transformation t) {
		transformations.push_back(t);
	}

	void addPrimitives(Primitive p) {
		primitives.push_back(p);
	}

	void addGroups(Group g) {
		groups.push_back(g);
	}

	void addPoint(Point p) {
		points.push_back(p);
	}

	void setPrimitives(std::vector<Primitive> primitives) {
        this->primitives = primitives;
	}

	~GroupBuilder() = default;
};

Group::Group() : groupBuilder{ new class GroupBuilder() } {}

Group::Group(std::vector<Primitive> primitives, std::vector<Group> groups, std::vector<Transformation> transformations, std::vector<Point> points) : groupBuilder{ new GroupBuilder(primitives,groups,transformations,points) } {}


int Group::getAngle() {
	return groupBuilder->getAngle();
}

void Group::setAngle(float angle) {
	return groupBuilder->setAngle(angle);
}


std::vector<Primitive> Group::getPrimitivas() {
	return groupBuilder->getPrimitivas();
}

std::vector<Transformation> Group::getTransformations() {
	return groupBuilder->getTransformations();
}


int Group::getNrPrimitives() {
	return groupBuilder->getNrPrimitives();
}

int Group::getNrGroups() {
	return groupBuilder->getNrGroups();
}

std::vector<Point> Group::getPoints() {
	return groupBuilder->getPoints();
}


int Group::getNrTransformations() {
	return groupBuilder->getNrTransformations();
}

void Group::addTransformation(Transformation t) {
	groupBuilder->addTransformation(t);
}

void Group::addPrimitives(Primitive p) {
	groupBuilder->addPrimitives(p);
}

void Group::addGroups(Group g) {
	groupBuilder->addGroups(g);
}

void Group::addPoint(Point p) {
	groupBuilder->addPoint(p);
}

void Group::setPrimitives(std::vector<Primitive> primitives) {
	groupBuilder->setPrimitives(primitives);
}

Transformation Group::getTransformation(int index) {
    return groupBuilder->getTransformation(index);
}

Primitive Group::getPrimitives(int index) {
    return groupBuilder->getPrimitives(index);
}

std::vector<Group> Group::getGroups() {
    return groupBuilder->getGroups();
}

Group Group::getGroup(int index){
    return groupBuilder->getGroup(index);
}




Group::~Group() {
    /*
	if (groupBuilder != nullptr) {
		delete groupBuilder;
		groupBuilder = nullptr;
	}
     */
}


