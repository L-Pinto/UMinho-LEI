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

	Primitive(int nrVertices, std::string textureID, Point diff, Point escp, Point emiss, Point amb, int type);

	//Primitive(const Primitive& p);

	//GETS

	int getTypePrimitive();

	std::string getTextureID();


	Point getDiff();
	Point getespc();
	Point getEmiss();
	Point getAmb();

    bool isDiff();

    bool isEspc();

    bool isEmiss();

    bool isAmb();

	//SETS	
	void setTextureID(std::string g);

	//void Primitive::setType(int t);
	void setType(int t);

	void setDiff(Point diff);

	void setEspc(Point espc);

	void setEmiss(Point emiss);

	void setAmb(Point amb);

    void setIsDiff(bool isdiff);

    void setIsAmb(bool isAmb);

    void setIsEspc(bool isEspec);

    void setIsEmiss(bool isEmiss);

	//ADDS
	void setNrVertices(int nrVertices);

	void addVertice();

	int getNrVertices();


	~Primitive();
	
};

#endif