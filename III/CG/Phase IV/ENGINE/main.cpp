#define  _USE_MATH_DEFINES
#include <math.h>
#include <IL/il.h>
#include <stdlib.h>
#include <GL/glew.h>
#include <GL/glut.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include "tinyxml2.h"
#include "Primitive.h"
#include "Point.h"
#include <cstring>
#include <string>
#include <algorithm>
#include "Group.h"
#include "Transformation.h"
#include "Light.h"
#include <map>
#include <iterator>

#ifdef __linux__
#include <unistd.h>
#elif _WIN32
#include <direct.h>
#include <io.h>
#else
#endif

using namespace tinyxml2;
using namespace std;

map<string, GLuint> texturas;

//fps variables
int timebase = 0, frame = 0;

// Mouse Motion Variables
int startX, startY, tracking = 0;
float camX = 0, camY, camZ = 5;

unsigned int texture;

// VBO variables
vector<float> vertexVBO, normalCoordVBO, textCoordVBO;
//float *vertexVBO, *normalCoordVBO, *textCoordVBO;
GLuint buffers[3], vertexcount;
GLuint ptr = 0;

// ----------------------------------------------------------
int POINT_COUNT = 1; //Catmull Rom number of points
vector<Point> p; //Vector to store Catmull Rom Points 
float yBefore[3] = { 0,1,0 };
float t = 0;
// ----------------------------------------------------------

//Vector that stores filenames.
vector<string> files;

//Vector that stores all Primitives.
vector<Group> groups;
vector<Light> lights;
 
string pathFile;

//Variables needed to the keyboard function.
float r = 4.0f;
float alpha = 0.5;
float beta = 0.5;


// VBO variables for the belts 
vector<float> ringVBO;
GLuint buffersRing[1];
GLuint ptrRing = 0;

/**
 * Function that redimensionates a window.
 * @param w width of the window.
 * @param h height of the window.
 */
void changeSize(int w, int h) {

    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if (h == 0)
        h = 1;

    // compute window's aspect ratio
    float ratio = w * 1.0 / h;

    // Set the projection matrix as current
    glMatrixMode(GL_PROJECTION);
    // Load Identity Matrix
    glLoadIdentity();

    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);

    // Set perspective
    gluPerspective(45.0f, ratio, 1.0f, 1000.0f);

    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


/**
 * Function that reads a file, given the filename.
 * @param file filename.
 * @return bool true if everything goes well. Otherwise, returns false.
 */
Primitive readFile(string file) { 

    ifstream MyReadFile(file);

    Primitive primitive;
    primitive.setNrVertices(0);

    string myText;
    int nvertices;
    int vertex = 0;

    int tipo = 0;

    getline(MyReadFile, myText);

    string ringStr = "ring";
    if (ringStr.compare(myText) == 0) { //check if is a ring
        getline(MyReadFile, myText);
        nvertices = stoi(myText);
        primitive.setType(1);//1 if are points

    }
    else {
        nvertices = stoi(myText);
        primitive.setType(0);
    }


    for (int i = 0; i < nvertices; i++) {
        // Vector of string to save tokens
        vector<float> tokens;

        // stringstream class check1
        getline(MyReadFile, myText);
        stringstream check1(myText);

        string intermediate;

        // Tokenizing w.r.t. space ' '
        while (getline(check1, intermediate, ',')) {
            tokens.push_back(stof(intermediate));
        }

        Point point;
        point.setX(tokens[0]);
        point.setY(tokens[1]);
        point.setZ(tokens[2]);

        if (primitive.getTypePrimitive() == 1) {
            ringVBO.push_back(tokens[0]);
            ringVBO.push_back(tokens[1]);
            ringVBO.push_back(tokens[2]);

            primitive.addVertice();
        }
        else { //if is not a ring
            
            Point normal;
            normal.setX(tokens[3]);
            normal.setY(tokens[4]);
            normal.setZ(tokens[5]);

            Point texture;
            texture.setX(tokens[6]);
            texture.setY(tokens[7]);
            texture.setZ(0); // not relevant

            //VBO
            vertexVBO.push_back(tokens[0]);
            vertexVBO.push_back(tokens[1]);
            vertexVBO.push_back(tokens[2]);

            //VBO normal
            normalCoordVBO.push_back(tokens[3]);
            normalCoordVBO.push_back(tokens[4]);
            normalCoordVBO.push_back(tokens[5]);

            //VBO texture
            textCoordVBO.push_back(tokens[6]);
            textCoordVBO.push_back(tokens[7]);

            primitive.addVertice();
        }    

    }

    MyReadFile.close();

    return primitive;
}

/**
 * Function that loads a given texture.
 * @param s file path to texture.
 * @return int texture id.
 */
int loadTexture(std::string s) {

    unsigned int t, tw, th;
    unsigned char* texData;
    unsigned GLuint;
    // for each image �

    ilInit();
    ilEnable(IL_ORIGIN_SET);
    ilOriginFunc(IL_ORIGIN_LOWER_LEFT);

    ilGenImages(1, &t);
    ilBindImage(t);
    ilLoadImage((ILstring)s.c_str());

    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);
    ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
    texData = ilGetData();

    // create a texture slot
    glGenTextures(1, &GLuint);
    // bind the slot
    glBindTexture(GL_TEXTURE_2D, GLuint);

    // define texture parameters
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    // send texture data to OpenGL
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
    //glGenerateMipmap(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, 0);

    return GLuint;

}


/**
 * Function that redimensionates a window.
 * @param time width of the window.
 * @param x - component x of the specified axis.
 * @param y - component y of the specified axis.
 * @param z - component z of the specified axis.
 * @param primitives - all the primitives to draw
 * @param transformations - all the transformations to apply
 * @param angle - rotation angle
 * @return vector<float> containing the new angle.
 */
vector<float> rotateTime(int time, float x, float y, float z, vector<Primitive> primitives, vector<Transformation> transformations, float angle) {

    string scale = "scale";
    string translate = "translate";
    string rotate = "rotate";
    string color = "color";

    glRotatef(angle, x, y, z);
    int teste = ptr;


    for (int j = 0; j < transformations.size(); j++) {
        Transformation t = transformations[j];

        if (translate.compare(t.getName()) == 0) {
            if (t.getTime() == 0) {
                glTranslatef(t.getX(), t.getY(), t.getZ());
            }
        }
        else if (scale.compare(t.getName()) == 0) {
            glScalef(t.getX(), t.getY(), t.getZ());
        }
        else if (color.compare(t.getName()) == 0) {
            glColor3f(t.getX(), t.getY(), t.getZ());
        }
        else if (rotate.compare(t.getName()) == 0) {
            if (t.getAngle() != 0 && t.getTime() == 0) {
                glRotatef(t.getAngle(), t.getX(), t.getY(), t.getZ());
            }
        }
    }

   
    //glRotatef(angle2, x2, y2, z3);

    string type = "type";

    for (int i = 0; i < primitives.size(); i++) {
        Primitive primitive = primitives[i];

        int nrVertices = primitive.getNrVertices();

        int tipo = primitive.getTypePrimitive();

        if (tipo == 1) { //if the primitive is just points

            glBindBuffer(GL_ARRAY_BUFFER, buffersRing[0]);
            glVertexPointer(3, GL_FLOAT, 0, 0);
            glDrawArrays(GL_POINTS, ptrRing, nrVertices * 3);

            ptrRing = ptrRing + nrVertices * 3;

        }
        else { //if it's a normal primitive

            if (primitive.isDiff()) {
                Point diffuse = primitive.getDiff();
                float dL[] = { diffuse.getX(), diffuse.getY(), diffuse.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, dL);
                glMaterialf(GL_FRONT,GL_SHININESS,128);
            }
            if (primitive.isEspc()) {
                Point specular = primitive.getespc();
                float sL[] = { specular.getX(), specular.getY(), specular.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_SPECULAR, sL);
                glMaterialf(GL_FRONT,GL_SHININESS,128);
            }
            if (primitive.isEmiss()) {
                Point emission = primitive.getEmiss();
                float eL[] = { emission.getX(), emission.getY(), emission.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_EMISSION, eL);
                glMaterialf(GL_FRONT,GL_SHININESS,128);
            }
            if (primitive.isAmb()) {
                Point ambient = primitive.getAmb();
                float aL[] = { ambient.getX(), ambient.getY(), ambient.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_AMBIENT, aL);
                glMaterialf(GL_FRONT,GL_SHININESS,128);
            }

            if (primitive.getTextureID().compare("null") != 0) {
                auto id = texturas.find(primitive.getTextureID());

                glBindTexture(GL_TEXTURE_2D, id->second);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
                glNormalPointer(GL_FLOAT, 0, 0);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
                glTexCoordPointer(2, GL_FLOAT, 0, 0);

                glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

                glBindTexture(GL_TEXTURE_2D, 0);

                ptr = ptr + nrVertices;

            }
            else { //caso primitiva nao tenha textura

                glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
                glNormalPointer(GL_FLOAT, 0, 0);

                glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

                ptr = ptr + nrVertices;
            }
        }
    }


    angle = ((float)glutGet(GLUT_ELAPSED_TIME) * 360 / 1000) / ((float)time);

    glutPostRedisplay();

    vector<float> res;

    res.push_back(angle);
    return res;
}

//---------------------------- CATMULL-ROM ---------------------------------

/**
 * Function that creates a rotation matrix.
 * @param x
 * @param y
 * @param z
 * @param m matrix
 */
void buildRotMatrix(float* x, float* y, float* z, float* m) {

    m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
    m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
    m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
    m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}

/**
 * Function that creates a rotation matrix.
 * @param x
 * @param y
 * @param z
 * @param m matrix
 */
void cross(float* a, float* b, float* res) {

    res[0] = a[1] * b[2] - a[2] * b[1];
    res[1] = a[2] * b[0] - a[0] * b[2];
    res[2] = a[0] * b[1] - a[1] * b[0];
}

/**
 * Function that normalizes a vector.
 * @param a - vector to normalize
 */
void normalize(float* a) {

    float l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    a[0] = a[0] / l;
    a[1] = a[1] / l;
    a[2] = a[2] / l;
}


/**
 * Function that calculates the norm of a vector.
 * @param a - vector to calculate
 */
float length(float* v) {

    float res = sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    return res;
}

/**
 * Function that calculates the product between a vector and a matrix.
 * @param m - matrix
 * @param v - vector
 * @param res - result
 */
void multMatrixVector(float* m, float* v, float* res) {

    for (int j = 0; j < 4; ++j) {
        res[j] = 0;
        for (int k = 0; k < 4; ++k) {
            res[j] += v[k] * m[j * 4 + k];
        }
    }

}

/**
 * Function that calculates a Catmull-Rom Point
 * @param t
 * @param p1 - Catmull Point
 * @param p2 - Catmull Point
 * @param p3 - Catmull Point
 * @param p4 - Catmull Point
 * @param res - result
 */
void getCatmullRomPoint(float t, Point p0, Point p1, Point p2, Point p3, float* res) {

    // catmull-rom matrix
    float m[4][4] = { {-0.5f,  1.5f, -1.5f,  0.5f},
                        { 1.0f, -2.5f,  2.0f, -0.5f},
                        {-0.5f,  0.0f,  0.5f,  0.0f},
                        { 0.0f,  1.0f,  0.0f,  0.0f} };


    float tt[4] = { t * t * t, t * t, t, 1 };

    float aux[4];

    float pp[4] = { p0.getX(), p1.getX(), p2.getX(), p3.getX() };
    multMatrixVector((float*)m, pp, aux);
    res[0] = tt[0] * aux[0] + tt[1] * aux[1] + tt[2] * aux[2] + tt[3] * aux[3];

    float pp1[4] = { p0.getY(), p1.getY(), p2.getY(), p3.getY() };
    multMatrixVector((float*)m, pp1, aux);
    res[1] = tt[0] * aux[0] + tt[1] * aux[1] + tt[2] * aux[2] + tt[3] * aux[3];

    float pp2[4] = { p0.getZ(), p1.getZ(), p2.getZ(), p3.getZ() };
    multMatrixVector((float*)m, pp2, aux);
    res[2] = tt[0] * aux[0] + tt[1] * aux[1] + tt[2] * aux[2] + tt[3] * aux[3];

}

/**
 * Function that calculates the derivative of Catmull-Rom Points
 * @param t
 * @param p1 - Catmull Point
 * @param p2 - Catmull Point
 * @param p3 - Catmull Point
 * @param p4 - Catmull Point
 * @param res - result
 */
void getDerivativeCatmullRomPoint(float t, Point p0, Point p1, Point p2, Point p3, float* res) {

    float m[4][4] = {
        {-0.5, 1.5f, -1.5f, 0.5f},
        { 1.0f, -2.5f, 2.0f, -0.5f},
        {-0.5f, 0.0f, 0.5f, 0.0f},
        {0.0f, 1.0f, 0.0f, 0.0f} };

    float td[4] = { 3 * t * t, 2 * t, 1, 0 };

    float aux[4];
    float pp[4] = { p0.getX(), p1.getX(), p2.getX(), p3.getX() };
    multMatrixVector((float*)m, pp, aux);
    res[0] = td[0] * aux[0] + td[1] * aux[1] + td[2] * aux[2] + td[3] * aux[3];


    float pp1[4] = { p0.getY(), p1.getY(), p2.getY(), p3.getY() };
    multMatrixVector((float*)m, pp1, aux);
    res[1] = td[0] * aux[0] + td[1] * aux[1] + td[2] * aux[2] + td[3] * aux[3];


    float pp2[4] = { p0.getZ(), p1.getZ(), p2.getZ(), p3.getZ() };
    multMatrixVector((float*)m, pp2, aux);
    res[2] = td[0] * aux[0] + td[1] * aux[1] + td[2] * aux[2] + td[3] * aux[3];

}


/**
 * Function that calculates a point and its derivative, given  global t
 * @param gt
 * @param pos - Catmull-Rom Point
 * @param derv - Derivative of Catmull-Rom Point
 */
void getGlobalCatmullRomPoint(float gt, float* pos, float* deriv) {

    float t = gt * POINT_COUNT; // this is the real global t
    int index = floor(t);  // which segment
    t = t - index; // where within  the segment

    // indices store the points
    int indices[4];
    indices[0] = (index + POINT_COUNT - 1) % POINT_COUNT;
    indices[1] = (indices[0] + 1) % POINT_COUNT;
    indices[2] = (indices[1] + 1) % POINT_COUNT;
    indices[3] = (indices[2] + 1) % POINT_COUNT;

    getCatmullRomPoint(t, p.at(indices[0]), p.at(indices[1]), p.at(indices[2]), p.at(indices[3]), pos);
    getDerivativeCatmullRomPoint(t, p.at(indices[0]), p.at(indices[1]), p.at(indices[2]), p.at(indices[3]), deriv);
}

/**
 * Function that renders the path of Catmull and draws the derivative of every point calculated
 */
void renderCatmullRomCurve() {

    // draw curve using line segments with GL_LINE_LOOP

    glDisable(GL_LIGHTING);
    float res[3], deriv[3];
    float t1 = 100.0f; 
    glBegin(GL_LINE_LOOP);
    for (int i = 0; i < t1; i += 1) {
        getGlobalCatmullRomPoint(i / t1, res, deriv);
        glVertex3fv(res);
    }
    glEnd();

    
    glBegin(GL_LINES);
    for (int i = 0; i < 100; i += 1) {
        getGlobalCatmullRomPoint(i / 100.0f, res, deriv);
        glVertex3fv(res);
        res[0] += deriv[0];
        res[1] += deriv[1];
        res[2] += deriv[2];
        glVertex3fv(res);
    }
    glEnd();
     
    glEnable(GL_LIGHTING);

}

/**
 * Function that moves the primitives along the Catmull Curve and applies transformations
 * @param time - time to run the whole curve
 * @param primitives - primitives to draw
 * @param transformations - transformations to apply
 */
void renderCatmull(float time, std::vector<Primitive> primitives, std::vector<Transformation> transformations) {

    string scale = "scale";
    string translate = "translate";
    string rotate = "rotate";
    string color = "color";

    float m[16] = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
        pos[4] = { 0,0,0,0 },
        deriv[4] = { 0,0,0,0 },
        x[3] = { 0,0,0 },
        yNew[3] = { 0,0,0 },
        z[3] = { 0,0,0 };

    glColor3f(0.4028, 0.4028, 0.4028);
    // renderCatmullRomCurve(); //draws catmull curve
    glPushMatrix();
    glEnable(GL_LIGHTING);
    
    t = ((float)glutGet(GLUT_ELAPSED_TIME) / 1000) / ((float)time);
    getGlobalCatmullRomPoint(t, pos, deriv);


    glTranslatef(pos[0], pos[1], pos[2]);

    for (int i = 0;i < 3;i++) x[i] = deriv[i]; 
    normalize(x);
    cross(x, yBefore, z);
    normalize(z);
    cross(z, x, yNew);
    normalize(yNew);
    for (int i = 0;i < 3;i++) yBefore[i] = yNew[i];  

    buildRotMatrix(x, yNew, z, m);
    
    glMultMatrixf(m);

    
    for (int j = 0; j < transformations.size(); j++) {
        Transformation t = transformations[j];

        if (translate.compare(t.getName()) == 0) {
            if (t.getTime() == 0) { 
                glTranslatef(t.getX(), t.getY(), t.getZ());
            }
        }
        else if (scale.compare(t.getName()) == 0) {
            glScalef(t.getX(), t.getY(), t.getZ());
        }
        else if (color.compare(t.getName()) == 0) {
            glColor3f(t.getX(), t.getY(), t.getZ());
        }
        else if (rotate.compare(t.getName()) == 0) {
            if (t.getAngle() != 0 && t.getTime() == 0) {
                glRotatef(t.getAngle(), t.getX(), t.getY(), t.getZ());
            }
        }
    }

    string type = "points";
    for (int i = 0; i < primitives.size(); i++) {
        Primitive primitive = primitives[i];

        int nrVertices = primitive.getNrVertices();

        int tipo = primitive.getTypePrimitive();

        if (tipo == 1) { //if the primitive is just points

            glBindBuffer(GL_ARRAY_BUFFER, buffersRing[0]);
            glVertexPointer(3, GL_FLOAT, 0, 0);
            glDrawArrays(GL_POINTS, ptrRing, nrVertices * 3);

            ptrRing = ptrRing + nrVertices * 3;

        }
        else { //if it's a normal primitive

            if (primitive.isDiff()) {
                Point diffuse = primitive.getDiff();
                float dL[] = { diffuse.getX(), diffuse.getY(), diffuse.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, dL);
                glMaterialf(GL_FRONT, GL_SHININESS, 128);
            }
            if (primitive.isEspc()) {
                Point specular = primitive.getespc();
                float sL[] = { specular.getX(), specular.getY(), specular.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_SPECULAR, sL);
                glMaterialf(GL_FRONT, GL_SHININESS, 128);
            }
            if (primitive.isEmiss()) {
                Point emission = primitive.getEmiss();
                float eL[] = { emission.getX(), emission.getY(), emission.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_EMISSION, eL);
                glMaterialf(GL_FRONT, GL_SHININESS, 128);
            }
            if (primitive.isAmb()) {
                Point ambient = primitive.getAmb();
                float aL[] = { ambient.getX(), ambient.getY(), ambient.getZ(), 1.0 };
                glMaterialfv(GL_FRONT, GL_AMBIENT, aL);
                glMaterialf(GL_FRONT, GL_SHININESS, 128);
            }

            if (primitive.getTextureID().compare("null") != 0) {
                auto id = texturas.find(primitive.getTextureID());

                glBindTexture(GL_TEXTURE_2D, id->second);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
                glNormalPointer(GL_FLOAT, 0, 0);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
                glTexCoordPointer(2, GL_FLOAT, 0, 0);

                glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

                glBindTexture(GL_TEXTURE_2D, 0);

                ptr = ptr + nrVertices;

            }
            else { //caso primitiva nao tenha textura

                glBindBuffer(GL_ARRAY_BUFFER,buffers[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);

                glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
                glNormalPointer(GL_FLOAT, 0, 0);

                glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

                ptr = ptr + nrVertices;
            }
        }
    }
    glDisable(GL_LIGHTING);
    glPopMatrix();
    
}

//-------------------------------------------------
/**
 * Function that draws all the primitives previously stored in a VBO.
*/
void drawPrimitives(Group group) { 

    string scale = "scale";
    string translate = "translate";
    string rotate = "rotate";
    string color = "color";
    float time = 0;

    int r_time = 0, t_time = 0; 
    float x, y, z;

    // Transformations
    for (int j = 0; j < group.getNrTransformations(); j++) {
        Transformation t = group.getTransformation(j);

        if (rotate.compare(t.getName()) == 0) {
            if (t.getAngle() == 0 || t.getTime() != 0) {
                time = t.getTime();

                r_time = 1;

                x = t.getX();
                y = t.getY();
                z = t.getZ();
            }
        } 
        else if (translate.compare(t.getName()) == 0) {
            if (t.getTime() != 0) { 
                time = t.getTime();
                t_time = 1;
            }
        }
    }

    if (r_time == 1) { 
        vector<float> res = rotateTime(time, x, y, z, group.getPrimitivas(), group.getTransformations(), group.getAngle());
        group.setAngle(res[0]);

    }
    else if (t_time == 1) {
        vector<Point> points = group.getPoints();

        p = points;
        POINT_COUNT = points.size();

        renderCatmull(time, group.getPrimitivas(), group.getTransformations());
    }
    else {
        //transformations
        for (int j = 0; j < group.getNrTransformations(); j++) {
            Transformation t = group.getTransformation(j);

            if (translate.compare(t.getName()) == 0) {
                if (t.getTime() == 0) { 
                    glTranslatef(t.getX(), t.getY(), t.getZ());
                }
            }
            else if (scale.compare(t.getName()) == 0) {
                glScalef(t.getX(), t.getY(), t.getZ());
            }
            else if (color.compare(t.getName()) == 0) {
                glColor3f(t.getX(), t.getY(), t.getZ());
            }
            else if (rotate.compare(t.getName()) == 0) {
                if (t.getAngle() != 0 && t.getTime() == 0) {
                    glRotatef(t.getAngle(), t.getX(), t.getY(), t.getZ());
                }
            }
        }

        //draw primitives with VBO
        for (int i = 0; i < group.getNrPrimitives(); i++) {
            Primitive primitive = group.getPrimitives(i);

            int nrVertices = primitive.getNrVertices();

            int tipo = primitive.getTypePrimitive();

            if (tipo == 1) { //if the primitive is points

                glBindBuffer(GL_ARRAY_BUFFER, buffersRing[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);
                glDrawArrays(GL_POINTS, ptrRing, nrVertices*3);

                ptrRing = ptrRing + nrVertices*3;

            }
            else { //if it's a normal primitive
                //apply material lights
                if (primitive.isDiff()) {
                    Point diffuse = primitive.getDiff();
                    float dL[] = { diffuse.getX(), diffuse.getY(), diffuse.getZ(), 1.0 };
                    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, dL);
                    glMaterialf(GL_FRONT, GL_SHININESS, 128);
                }
                if (primitive.isEspc()) {
                    Point specular = primitive.getespc();
                    float sL[] = { specular.getX(), specular.getY(), specular.getZ(), 1.0 };
                    glMaterialfv(GL_FRONT, GL_SPECULAR, sL);
                    glMaterialf(GL_FRONT, GL_SHININESS, 128);
                }
                if (primitive.isEmiss()) {
                    Point emission = primitive.getEmiss();
                    float eL[] = { emission.getX(), emission.getY(), emission.getZ(), 1.0 };
                    glMaterialfv(GL_FRONT, GL_EMISSION, eL);
                    glMaterialf(GL_FRONT, GL_SHININESS, 128);
                }
                if (primitive.isAmb()) {
                    Point ambient = primitive.getAmb();
                    float aL[] = { ambient.getX(), ambient.getY(), ambient.getZ(), 1.0 };
                    glMaterialfv(GL_FRONT, GL_AMBIENT, aL);
                    glMaterialf(GL_FRONT, GL_SHININESS, 128);
                }

                if (primitive.getTextureID().compare("null") != 0) {
                    auto id = texturas.find(primitive.getTextureID());

                    glBindTexture(GL_TEXTURE_2D, id->second);

                    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
                    glVertexPointer(3, GL_FLOAT, 0, 0);

                    glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
                    glNormalPointer(GL_FLOAT, 0, 0);

                    glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
                    glTexCoordPointer(2, GL_FLOAT, 0, 0);

                    glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

                    glBindTexture(GL_TEXTURE_2D, 0);

                    ptr = ptr + nrVertices;

                }
                else { //caso primitiva nao tenha textura

                    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
                    glVertexPointer(3, GL_FLOAT, 0, 0);

                    glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
                    glNormalPointer(GL_FLOAT, 0, 0);

                    glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
                    glTexCoordPointer(2, GL_FLOAT, 0, 0);
                        
                    glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

                    ptr = ptr + nrVertices;
                }
            }
        }
    
    }

    //subgroups
    for (int z = 0; z < group.getNrGroups(); z++) {
        glPushMatrix(); glPushAttrib(GL_LIGHTING_BIT);
        drawPrimitives(group.getGroup(z));
        glPopMatrix(); glPopAttrib();
    }

}

/**
 * Function that creates a scene.
 */
void renderScene(void) {
    float fps;
    int timet;
    char s[64];
    float pos[4] = { 1.0, 1.0, 1.0, 0.0 };
    float red[4] = { 1,0,0,1 };
    float green[4] = { 0,1,0,1 };
    float blue[4] = { 0,0,1,1 };
    float white[4] = { 1,1,1,1 };
    GLfloat black[4] = { 0,0,0,1 };
    float def[4] = { 0.2, 0.2, 0.2, 1.0 };

    // clear buffers
    glClearColor(0,0,0,0);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    gluLookAt(r*camX, r*camY, r*camZ,
              0.0, 0.0, 0.0,
              0.0f, 1.0f, 0.0f);

    //--------
    //Lights
    for (int i = 0; i<lights.size(); i++) {
        Point p = lights[i].getCoordenates();

        if (lights[i].getType() == "POINT") {

            float pos[4] = { p.getX(), p.getY(), p.getZ(), 0.0 };

            glLightfv(GL_LIGHT0, GL_POSITION, pos);
        }
        else if (lights[i].getType() == "DIRECTIONAL") {
            float posD[4] = { p.getX(), p.getY(), p.getZ(), 1.0 };
            // place light
            glLightfv(GL_LIGHT0, GL_POSITION, posD);
        }
        else if (lights[i].getType() == "SPOT") {

            float posS[3] = { p.getX(), p.getY(), p.getZ() };
            // place light
            glLightfv(GL_LIGHT0, GL_SPOT_CUTOFF, posS);
        }
    }
    //-----
    //AXIS
    glBegin(GL_LINES);
    // X axis in red
    glMaterialfv(GL_FRONT, GL_AMBIENT, red); glVertex3f(-100.0f, 0.0f, 0.0f); glVertex3f(100.0f, 0.0f, 0.0f);
    // Y Axis in green
    glMaterialfv(GL_FRONT, GL_AMBIENT, green); glVertex3f(0.0f, -100.0f, 0.0f); glVertex3f(0.0f, 100.0f, 0.0f);
    // Z Axis in blue
    glMaterialfv(GL_FRONT, GL_AMBIENT, blue); glVertex3f(0.0f, 0.0f, -100.0f); glVertex3f(0.0f, 0.0f, 100.0f);
    // set to default
    glMaterialfv(GL_FRONT, GL_AMBIENT, def);
    glEnd();



    //draw groups
    for (int i = 0; i < groups.size(); i++) {
        glPushMatrix(); glPushAttrib(GL_LIGHTING_BIT);
        drawPrimitives(groups[i]);
        glPopMatrix(); glPopAttrib();
    }

    ptr = 0;
    ptrRing = 0;

    frame++;
    timet = glutGet(GLUT_ELAPSED_TIME);
    if (timet - timebase > 1000) {
        fps = frame * 1000.0 / (timet - timebase);
        timebase = timet;
        frame = 0;
        sprintf(s, "FPS: %f6.2", fps);
        glutSetWindowTitle(s);
    }

    // End of frame
    glutSwapBuffers();
}


/**
 * Function that processes mouse buttons
 * @param button
 * @param state
 * @param xx
 * @param yy
 */
void processMouseButtons(int button, int state, int xx, int yy)
{
    if (state == GLUT_DOWN) {
        startX = xx;
        startY = yy;
        if (button == GLUT_LEFT_BUTTON)
            tracking = 1;
        else if (button == GLUT_RIGHT_BUTTON)
            tracking = 2;
        else
            tracking = 0;
    }
    else if (state == GLUT_UP) {
        if (tracking == 1) {
            alpha += (xx - startX);
            beta += (yy - startY);
        }
        else if (tracking == 2) {

            r -= yy - startY;
            if (r < 3)
                r = 3.0;
        }
        tracking = 0;
    }
}

/**
 * Function that processes mouse motions
 * @param xx
 * @param yy
 */
void processMouseMotion(int xx, int yy)
{
    int deltaX, deltaY;
    int alphaAux, betaAux;
    int rAux;

    if (!tracking)
        return;

    deltaX = xx - startX;
    deltaY = yy - startY;

    if (tracking == 1) {

        alphaAux = alpha + deltaX;
        betaAux = beta + deltaY;

        if (betaAux > 85.0)
            betaAux = 85.0;
        else if (betaAux < -85.0)
            betaAux = -85.0;

        rAux = r;
    }
    else if (tracking == 2) {

        alphaAux = alpha;
        betaAux = beta;
        rAux = r - deltaY;
        if (rAux < 3)
            rAux = 3;
    }
    camX = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camZ = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camY = rAux * sin(betaAux * 3.14 / 180.0);
}


/**
 * Function that reacts to events.
 * @param key_code
 * @param x
 * @param y
 */
void mover(int key_code, int x, int y) {
    float change = M_PI / 40;
    switch (key_code) {
        case GLUT_KEY_LEFT: {
            beta -= change;
            break;
        }
        case GLUT_KEY_RIGHT: {
            beta += change;
            break;
        }
        case GLUT_KEY_UP: {
            if (alpha < (M_PI / 2))alpha += change;
            break;
        }
        case GLUT_KEY_DOWN: {
            if ( alpha>-(M_PI / 2) )alpha -= change;
            break;
        }
        case GLUT_KEY_PAGE_UP: {
            r += 0.1;
            break;
        }
        case GLUT_KEY_PAGE_DOWN: {
            r -= 0.1;
            break;
        }
    }
    glutPostRedisplay();
}
/**
* Function that founds the path to a certain image.
* @param image
*/
string getImagePath(const char* image) {

    char tmp[256];

    getcwd(tmp, 256); //tmp which contains the directory

    string path(tmp);

    int found = path.find("ENGINE"); // finds engine's localization

    replace(path.begin(), path.end(), '\\', '/');
    path.erase(path.begin() + found, path.end());
    string img = image;

    //caminho para o ficheiro XML
    path = path + "MODELS/IMAGES/" + img;

    return path;
}

/**
 * Function that parses elements within a model.
 * @param model model to parse.
 */
void readConfigModel(XMLElement *model, Primitive primitive) {

    const XMLAttribute* modelImag;
    const XMLAttribute* modelDiffR, *modelDiffG, *modelDiffB;
    const XMLAttribute* modelSpecR, *modelSpecG, *modelSpecB;
    const XMLAttribute* modelAmbR, *modelAmbG, *modelAmbB;
    const XMLAttribute* modelEmissR, *modelEmissG, *modelEmissB;

    modelImag = model->FindAttribute("texture");

    modelDiffR = model->FindAttribute("diffR");
    modelDiffG = model->FindAttribute("diffG");
    modelDiffB = model->FindAttribute("diffB");

    modelSpecR = model->FindAttribute("specR");
    modelSpecG = model->FindAttribute("specG");
    modelSpecB = model->FindAttribute("specB");

    modelEmissR = model->FindAttribute("emissR");
    modelEmissG = model->FindAttribute("emissG");
    modelEmissB = model->FindAttribute("emissB");

    modelAmbR = model->FindAttribute("ambR");
    modelAmbG = model->FindAttribute("ambG");
    modelAmbB = model->FindAttribute("ambB");


    if (modelImag != NULL) {
        const char* image = modelImag->Value();
        string img = getImagePath(image);

        primitive.setTextureID(img);
        texturas.insert(pair<string, GLuint>(img, -1));
    }
    else primitive.setTextureID("null");

    if(modelDiffR != NULL && modelDiffG != NULL && modelDiffB != NULL) {
        float r = modelDiffR->FloatValue();
        float g = modelDiffG->FloatValue();
        float b = modelDiffB->FloatValue();

        Point p;
        p.setX(r);
        p.setY(g);
        p.setZ(b);

        primitive.setDiff(p);
        primitive.setIsDiff(true);

    } else primitive.setIsDiff(false);

    if(modelSpecR != NULL && modelSpecG != NULL && modelSpecB != NULL) {
        float r = modelSpecR->FloatValue();
        float g = modelSpecG->FloatValue();
        float b = modelSpecB->FloatValue();

        Point p;
        p.setX(r);
        p.setY(g);
        p.setZ(b);

        primitive.setEspc(p);
        primitive.setIsEspc(true);

    } else primitive.setIsEspc(false);

    if(modelEmissR != NULL && modelEmissG != NULL && modelEmissB != NULL) {
        float r = modelEmissR->FloatValue();
        float g = modelEmissG->FloatValue();
        float b = modelEmissB->FloatValue();

        Point p;
        p.setX(r);
        p.setY(g);
        p.setZ(b);

        primitive.setEmiss(p);
        primitive.setIsEmiss(true);

    } else primitive.setIsEmiss(false);

    if(modelAmbR != NULL && modelAmbG != NULL && modelAmbB != NULL) {
        float r = modelAmbR->FloatValue();
        float g = modelAmbG->FloatValue();
        float b = modelAmbB->FloatValue();

        Point p;
        p.setX(r);
        p.setY(g);
        p.setZ(b);

        primitive.setAmb(p);
        primitive.setIsAmb(true);

    } else primitive.setIsAmb(false);
}


/**
 * Function that stores all the elements in the group
 * @param group that contains all the information
 * @param father 1 - if its a subgroup; 0 - if its a group
 * @return element to store in the vector
 */
Group parseGroup(XMLElement* group, int father) {

    string models = "models";
    string scale = "scale";
    string translate = "translate";
    string rotate = "rotate";
    string grupo = "group";
    string color = "color";
    string point = "point";
    Group g;

    do {
        g = Group();
        XMLElement* element = group->FirstChildElement();

        while (element != nullptr) {

            if (models.compare(element->Name()) == 0) {

                XMLElement* file = element->FirstChildElement("model");

                while (file != nullptr) {
                    const char* strfile = file->Attribute("file");
                    string namefile = strfile;
                    string path = pathFile + namefile;
                    Primitive primitive = readFile(path);

                    readConfigModel(file, primitive);
                    g.addPrimitives(primitive);

                    file = file->NextSiblingElement();                            
                }

            }
            else if (scale.compare(element->Name()) == 0) {
                float x, y, z;

                element->QueryFloatAttribute("x", &x);
                element->QueryFloatAttribute("y", &y);
                element->QueryFloatAttribute("z", &z);

                Transformation t = Transformation("scale", x, y, z, 0, 0);

                g.addTransformation(t);

            }
            else if (translate.compare(element->Name()) == 0) {
                float x, y, z, time;

                if (element->FindAttribute("x")) {
                    element->QueryFloatAttribute("x", &x);
                    element->QueryFloatAttribute("y", &y);
                    element->QueryFloatAttribute("z", &z);

                    Transformation t = Transformation("translate", x, y, z, 0, 0);
                    g.addTransformation(t);
                }
                else if (element->FindAttribute("time")) {
                    float x, y, z;
                    element->QueryFloatAttribute("time", &time);
                    Transformation t = Transformation("translate", 0, 0, 0, 0, time);
                    g.addTransformation(t);


                    XMLElement* point = element->FirstChildElement("point");

                    while (point != nullptr) {

                        const char *xPtr = point->Attribute("x");
                        const char *yPtr = point->Attribute("y");
                        const char *zPtr = point->Attribute("z");

                        Point pt;
                        pt.setX(stof(xPtr));
                        pt.setY(stof(yPtr));
                        pt.setZ(stof(zPtr));

                        g.addPoint(pt);

                        point = point->NextSiblingElement();
                    }
                }

            }
            else if (rotate.compare(element->Name()) == 0) {
                float x, y, z, angle, time;

                element->QueryFloatAttribute("x", &x);
                element->QueryFloatAttribute("y", &y);
                element->QueryFloatAttribute("z", &z);

                if (element->FindAttribute("angle")) {
                    element->QueryFloatAttribute("angle", &angle);

                    Transformation t = Transformation("rotate", x, y, z, angle, 0);
                    g.addTransformation(t);
                }
                else if (element->FindAttribute("time")) {
                    element->QueryFloatAttribute("time", &time);
                    
                    Transformation t = Transformation("rotate", x, y, z, 0, time);
                    g.addTransformation(t);
                }

            }
            else if (grupo.compare(element->Name()) == 0) {

                Group gr = parseGroup(element, 1); 
                g.addGroups(gr);

            }
            else if (color.compare(element->Name()) == 0) {

                float red, green, blue;

                element->QueryFloatAttribute("x", &red);
                element->QueryFloatAttribute("y", &green);
                element->QueryFloatAttribute("z", &blue);

                Transformation t = Transformation("color", red, green, blue, 0, 0);

                g.addTransformation(t);

            }


            element = element->NextSiblingElement();

            if (element == NULL && father == 1) return g;
        }

        if (father == 0) groups.push_back(g); 
        group = group->NextSiblingElement();

    } while (group != nullptr);

   
    return g;
}


/*
 * Function that reads light from a xml file.
 */
void parseLights(XMLElement* element) {

    XMLElement* light = element->FirstChildElement();

    const XMLAttribute* type = light->FindAttribute("type");
    const XMLAttribute* posX = light->FindAttribute("posX");
    const XMLAttribute* posY = light->FindAttribute("posY");
    const XMLAttribute* posZ = light->FindAttribute("posZ");

    const char* t = type->Value();
    float x = posX->FloatValue();
    float y = posY->FloatValue();
    float z = posZ->FloatValue();

    string tp = t;

    Light l = Light();

    l.setType(tp);

    Point p;
    p.setX(x);
    p.setY(y);
    p.setZ(z);

    l.setCoordenates(p); //erro

    lights.push_back(l);
}

/**
 * Function that reads a XML file.
 * @return bool true if everything goes well. Otherwise, returns false.
 */
bool parseDocument(char* path1) {

    XMLDocument doc;

    string aux(path1);
    replace(aux.begin(), aux.end(), '\\', '/');
    doc.LoadFile(path1);

    XMLNode* scene = doc.FirstChild();
    if (scene == nullptr) {
        cout << "ERRO";
        return false; //in case of error
    }

    XMLElement* lights = scene->FirstChildElement("lights");
    if (lights != nullptr) {
        parseLights(lights);
    }

    XMLElement* group = scene->FirstChildElement("group");
    parseGroup(group, 0);

    return true;
}

void initGL() {

    //  OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    //glPolygonMode(GL_FRONT, GL_LINE);

    //init
    glEnableClientState(GL_VERTEX_ARRAY);
    //glEnableClientState(GL_INDEX_ARRAY);
    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);

    glClearColor(0,0,0,0);

    // enable light
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_RESCALE_NORMAL);
    glEnable(GL_TEXTURE_2D);


    //vertices
    glGenBuffers(3, buffers);
    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * vertexVBO.size(), &(vertexVBO[0]), GL_STATIC_DRAW);
    //normais
    glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * normalCoordVBO.size(), &(normalCoordVBO[0]), GL_STATIC_DRAW);
    //texturas
    glBindBuffer(GL_ARRAY_BUFFER, buffers[2]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * textCoordVBO.size(), &(textCoordVBO[0]), GL_STATIC_DRAW);


    //Create VBO Rings
    glGenBuffers(1, buffersRing);
    glBindBuffer(GL_ARRAY_BUFFER, buffersRing[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * ringVBO.size(), ringVBO.data(), GL_STATIC_DRAW);
}

/**
 * Function that inits glut.
 * @param argc size of array.
 * @param argv array with arguments.
 * @return bool true if everything goes well. Otherwise, returns false.
 */
bool initGlut(int argc, char** argv) {

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
    glutInitWindowPosition(100, 100);
    glutInitWindowSize(800, 800);
    glutCreateWindow("TP4-CG");

    // Required callback registry
    glutIdleFunc(renderScene);
    glutDisplayFunc(renderScene);
    glutReshapeFunc(changeSize);

    // put here the registration of the keyboard callbacks

    glutMouseFunc(processMouseButtons);
    glutSpecialFunc(mover);
    glutMotionFunc(processMouseMotion);
    
    glewInit();
    initGL();

    
    std::map<std::string, GLuint>::iterator it = texturas.begin();

    while (it != texturas.end())
    {
        std::string file = it->first;
        it->second = loadTexture(file);
        it++;
    }

    // enter GLUT's main cycle
    glutMainLoop();
    return true;
}


/**
 * Main Function.
 * @param argc size of array.
 * @param argv array with arguments.
 * @return int 
 */
int main(int argc, char** argv) {

    bool res;
    char tmp[256];
    getcwd(tmp, 256); //tmp which contains the directory

    string path(tmp);
    int found = path.find("ENGINE"); // finds engine's localization

    replace(path.begin(), path.end(), '\\', '/');
    path.erase(path.begin() + found, path.end());

    path = path + "MODELS/";
    pathFile = path;

    if (parseDocument(argv[1])) {
        initGlut(argc, argv);
    }
    else {
        cout << "XML File Invalid";
    }
    
    return 1;
}

