#define  _USE_MATH_DEFINES
#include <math.h>
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

#ifdef __linux__
#include <unistd.h>
#elif _WIN32
#include <direct.h>
#include <io.h>
#else
#endif

using namespace tinyxml2;
using namespace std;

// Mouse Motion Variables
int startX, startY, tracking = 0;
float camX = 0, camY, camZ = 5;

// VBO variables
vector<float> vertexB;
GLuint buffers[1];
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

    string myText;
    bool isRing = false;
    int vertices;

    int tipo = 0;

    getline(MyReadFile, myText);

    string ringStr = "ring"; 
    if (ringStr.compare(myText) == 0) { //check if is a ring
        getline(MyReadFile, myText);
        vertices = stoi(myText);
        isRing = true;
        primitive.setType(1);//1 if are points
    }
    else {
        vertices = stoi(myText);
        primitive.setType(0);
    }

    for (int i = 0; i < vertices; i++) {
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

        if (isRing) {
            ringVBO.push_back(tokens[0]);
            ringVBO.push_back(tokens[1]);
            ringVBO.push_back(tokens[2]);
        }
        else { //if is not a ring
            //VBO
            vertexB.push_back(tokens[0]);
            vertexB.push_back(tokens[1]);
            vertexB.push_back(tokens[2]);
            
        }

        primitive.addPoint(point);
    }


    MyReadFile.close();

    return primitive;
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

            glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
            glVertexPointer(3, GL_FLOAT, 0, 0);
            glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

            ptr = ptr + nrVertices;
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

    //glDisable(GL_LIGHTING);
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
     
    //glEnable(GL_LIGHTING);

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

            glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
            glVertexPointer(3, GL_FLOAT, 0, 0);
            glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

            ptr = ptr + nrVertices;
        }
    }

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
           
        string type = "points";
        //draw primitives with VBO

        for (int i = 0; i < group.getNrPrimitives(); i++) {
            Primitive primitive = group.getPrimitives(i);

            int nrVertices = primitive.getNrVertices();

            int tipo = primitive.getTypePrimitive();

            if (tipo == 1) { //if the primitive is just points

                glBindBuffer(GL_ARRAY_BUFFER, buffersRing[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);
                glDrawArrays(GL_POINTS, ptrRing, nrVertices*3);

                ptrRing = ptrRing + nrVertices*3;

            }
            else { //if it's a normal primitive

                glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
                glVertexPointer(3, GL_FLOAT, 0, 0);
                glDrawArrays(GL_TRIANGLES, ptr, nrVertices);

                ptr = ptr + nrVertices;
            }
            
        }
    
    }


    //subgroups
    for (int z = 0; z < group.getNrGroups(); z++) {
        glPushMatrix();
        drawPrimitives(group.getGroup(z));
        glPopMatrix();
    }

}

/**
 * Function that creates a scene.
 */
void renderScene(void) {

    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    /*
    gluLookAt(sin(beta) * cos(alpha) * r, sin(alpha) * r, cos(beta) * cos(alpha) * r, //camera position
        0.0, 0.0, 0.0,  // where to look
        0.0f, 1.0f, 0.0f); //up vector
    */
    gluLookAt(r*camX, r*camY, r*camZ,
        0.0, 0.0, 0.0,
        0.0f, 1.0f, 0.0f);
    
    
    //AXIS
    glBegin(GL_LINES);
    // X axis in red
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(-100.0f, 0.0f, 0.0f);
    glVertex3f(100.0f, 0.0f, 0.0f);
    // Y Axis in green
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, -100.0f, 0.0f);
    glVertex3f(0.0f, 100.0f, 0.0f);
    // Z Axis in blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, -100.0f);
    glVertex3f(0.0f, 0.0f, 100.0f);
    glEnd();
    

    //DRAW POINTS

    //draw groups
    for (int i = 0; i < groups.size(); i++) {
        glPushMatrix();
        drawPrimitives(groups[i]);
        glPopMatrix();
    }
 
    ptr = 0;
    ptrRing = 0;

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
    glutCreateWindow("TP-CG");

    // Required callback registry
    glutDisplayFunc(renderScene);
    glutIdleFunc(renderScene);
    glutReshapeFunc(changeSize);

    // put here the registration of the keyboard callbacks
    glutSpecialFunc(mover);
    glutMouseFunc(processMouseButtons);
    glutMotionFunc(processMouseMotion);

    glewInit();


    //  OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_VERTEX_ARRAY);
    glPolygonMode(GL_FRONT, GL_LINE);

    //glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_INDEX_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);


    //Create VBO
    glGenBuffers(1, buffers);

    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * vertexB.size(), vertexB.data(), GL_STATIC_DRAW);

    //Create VBO Rings
    glGenBuffers(2, buffersRing);

    glBindBuffer(GL_ARRAY_BUFFER, buffersRing[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * ringVBO.size(), ringVBO.data(), GL_STATIC_DRAW);


    // enter GLUT's main cycle
    glutMainLoop();
    return true;
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

                        const char* xPtr = point->Attribute("x");
                        const char* yPtr = point->Attribute("y");
                        const char* zPtr = point->Attribute("z");

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


/**
 * Function that reads a XML file.
 * @return bool true if everything goes well. Otherwise, returns false.
 */
bool parseDocument() {

    char tmp[256];

    getcwd(tmp, 256); //tmp which contains the directory

    string path(tmp);

    int found = path.find("ENGINE"); // finds engine's localization

    replace(path.begin(), path.end(), '\\', '/');
    path.erase(path.begin() + found, path.end());

    //caminho para o ficheiro XML 
    path = path + "MODELS/solarSystem.xml";

    strcpy(tmp, path.c_str());

    XMLDocument doc;
    doc.LoadFile(tmp);

    XMLNode* scene = doc.FirstChild();
    if (scene == nullptr) {
        cout << "ERRO";
        return false; //in case of error
    }


    XMLElement* group = scene->FirstChildElement("group"); 
    parseGroup(group, 0);

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



    if (parseDocument()) {
        groups[0];
        initGlut(argc, argv);
    } else{
        cout << "XML File Invalid";
    }

    
    
    return 1;
}

