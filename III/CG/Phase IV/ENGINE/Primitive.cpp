#include <iterator>
#include <string>

#include <GL/glut.h>
#include "Primitive.h"


class Primitive::PrimitiveBuilder {

    //vari�vel privada que guarda os v�rtices
private:

    int nrVertices;

    std::string textureID;

    Point diff;
    bool isDiff;
    Point espc;
    bool isEspc;
    Point emiss;
    bool isEmiss;
    Point amb;
    bool isAmb;

    int type;

public:
    PrimitiveBuilder() = default;

    PrimitiveBuilder(int nrVertices, std::string textureID, Point diff, Point espc, Point emiss, Point amb, int type) {
        this->nrVertices = nrVertices;
        this->textureID = textureID;
        this->diff = diff;
        this->espc = espc;
        this->emiss = emiss;
        this->amb = amb;

        this->type = type;

    }

    //GETS
    std::string getTextureID() {
        return textureID;
    }

    int getTypePrimitive() {
        return type;
    }

    Point getDiff() {
        return diff;
    }

    Point getespc() {
        return espc;
    }

    Point getEmiss() {
        return emiss;
    }

    void setIsDiff(bool isDiff) {
        this->isDiff = isDiff;
    }

    void setIsEspc(bool isEspc) {
        this->isEspc = isEspc;
    }

    void setIsEmiss(bool isEmiss) {
        this->isEmiss = isEmiss;
    }

    void setIsAmb(bool isAmb) {
        this->isAmb = isAmb;
    }

    Point getAmb() {
        return amb;
    }


    //SETS
    void setType(int t) {
        this->type = t;
    }

    void setTextureID(std::string textureID) {
        this->textureID = textureID;
    }

    void setDiff(Point diff) {
        this->diff.setX(diff.getX());
        this->diff.setY(diff.getY());
        this->diff.setZ(diff.getZ());
    }

    void setEspc(Point espc) {
        this->espc.setX(espc.getX());
        this->espc.setY(espc.getY());
        this->espc.setZ(espc.getZ());
    }

    void setEmiss(Point emiss) {
        this->emiss.setX(emiss.getX());
        this->emiss.setY(emiss.getY());
        this->emiss.setZ(emiss.getZ());
    }

    void setAmb(Point amb) {
        this->amb.setX(amb.getX());
        this->amb.setY(amb.getY());
        this->amb.setZ(amb.getZ());
    }


    bool isDiff1() {
        return this->isDiff;
    }

    bool isEspc1() {
        return this->isEspc;
    }

    bool isEmiss1() {
        return this->isEmiss;
    }

    bool isAmb1() {
        return this->isAmb;
    }


    int getNrVertices() {
        return this->nrVertices;
    }


    void setNrVertices(int nrVertices) {
        this->nrVertices = nrVertices;
    }

    void addVertice() {
        this->nrVertices = this->nrVertices + 1;
    }

    ~PrimitiveBuilder() = default;

};


Primitive::Primitive() : primitiveBuilder{new class PrimitiveBuilder()} {}

//Primitive::Primitive(std::vector<Point> vertices, std::vector<Point> texCoord, std::vector<Point> normals,
 //                    GLuint textureID, Point diff, Point espc, Point emiss, Point amb, int type) : primitiveBuilder{
  //      new PrimitiveBuilder(vertices, texCoord, normals, textureID, diff, espc, emiss, amb, type)} {}
  /*
Primitive::Primitive(Primitive p) : primitiveBuilder{
        new PrimitiveBuilder(p.primitiveBuilder->getVertices(), p.primitiveBuilder->getTexCoord(),
                             p.primitiveBuilder->getNormals(), p.primitiveBuilder->getTextureID(),
                             p.primitiveBuilder->getDiff(), p.primitiveBuilder->getespc(),
                             p.primitiveBuilder->getEmiss(), p.primitiveBuilder->getAmb(),
                             p.primitiveBuilder->getTypePrimitive())} {}
                             */

//GETS

int Primitive::getNrVertices() {
    return primitiveBuilder->getNrVertices();
}

void Primitive::setNrVertices(int nrVertices) {
    primitiveBuilder->setNrVertices(nrVertices);
}

void Primitive::addVertice() {
    primitiveBuilder->addVertice();
}


std::string Primitive::getTextureID() {
    return primitiveBuilder->getTextureID();
}

int Primitive::getTypePrimitive() {
    return primitiveBuilder->getTypePrimitive();
}


Point Primitive::getDiff() {
    return primitiveBuilder->getDiff();
}

Point Primitive::getespc() {
    return primitiveBuilder->getespc();
}

Point Primitive::getEmiss() {
    return primitiveBuilder->getEmiss();
}

Point Primitive::getAmb() {
    return primitiveBuilder->getAmb();
}

bool Primitive::isDiff() {
    return primitiveBuilder->isDiff1();
}

bool Primitive::isEspc() {
    return primitiveBuilder->isEspc1();
}

bool Primitive::isEmiss() {
    return primitiveBuilder->isEmiss1();
}

bool Primitive::isAmb() {
    return primitiveBuilder->isAmb1();
}

void Primitive::setIsDiff(bool isdiff) {
    primitiveBuilder->setIsDiff(isdiff);
}

void Primitive::setIsAmb(bool isAmb) {
    primitiveBuilder->setIsAmb(isAmb);
}

void Primitive::setIsEspc(bool isEspec) {
    primitiveBuilder->setIsEspc(isEspec);
}

void Primitive::setIsEmiss(bool isEmiss) {
    primitiveBuilder->setIsEmiss(isEmiss);
}

//SETS
void Primitive::setTextureID(std::string g) {
    primitiveBuilder->setTextureID(g);
}

void Primitive::setType(int t) {
    primitiveBuilder->setType(t);
}

void Primitive::setDiff(Point diff) {
    primitiveBuilder->setDiff(diff);
}

void Primitive::setEspc(Point espc) {
    primitiveBuilder->setEspc(espc);
}

void Primitive::setEmiss(Point emiss) {
    primitiveBuilder->setEmiss(emiss);
}

void Primitive::setAmb(Point amb) {
    primitiveBuilder->setAmb(amb);
}



Primitive::~Primitive() {
    /*
    if (primitiveBuilder != nullptr) {
        delete primitiveBuilder;
        //primitiveBuilder = nullptr;
    }
     */
}