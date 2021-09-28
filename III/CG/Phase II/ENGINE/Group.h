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

	Group(std::vector<Primitive> primitives, std::vector<Group> groups, std::vector<Transformation> transformations);

	int getNrPrimitives();

	int getNrGroups();

	int getNrTransformations();

	void addTransformation(Transformation t);

	void addPrimitives(Primitive p);

	void addGroups(Group g);

	void setPrimitives(std::vector<Primitive> primitives);

    Transformation getTransformation(int index);

    Primitive getPrimitives(int index);

    std::vector<Group> getGroups();

    Group getGroup(int index);

	~Group();


};

#endif