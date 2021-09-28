#ifndef Group_h
#define Group_h

#include <vector>
#include "Group.h"
#include "Primitive.h"
#include "Transformation.h"

class Group{
	class GroupBuilder;
	GroupBuilder* groupBuilder;

public:

	Group();

	Group(std::vector<Primitive> primitives, std::vector<Group> groups, std::vector<Transformation> transformations, std::vector<Point> points);

	int Group::getAngle();

	void Group::setAngle(float angle);

	std::vector<Primitive> getPrimitivas();

	std::vector<Transformation> Group::getTransformations();

	int getNrPrimitives();

	int getNrGroups();

	int getNrTransformations();

	std::vector<Point> getPoints();

	void addTransformation(Transformation t);

	void addPrimitives(Primitive p);

	void addGroups(Group g);

	void addPoint(Point p);

	void setPrimitives(std::vector<Primitive> primitives);

    Transformation getTransformation(int index);

    Primitive getPrimitives(int index);

    std::vector<Group> getGroups();

    Group getGroup(int index);

	~Group();


};

#endif