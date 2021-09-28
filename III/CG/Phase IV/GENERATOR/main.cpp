#define _USE_MATH_DEFINES

#include <stdlib.h>
#include <string>
#include <algorithm>
#include <vector>
#include <cmath>
#include <iostream>
#include <fstream>
#include "tinyxml2.h"
#include "Point.h"
#include <cstring>

#ifdef __linux__

#include <unistd.h>
#include <sstream>

#elif _WIN32
#include <direct.h>
#include <io.h>
#else
#endif

using namespace tinyxml2;
using namespace std;


/**
 * Function that writes in the 3d file and in the default xml file.
 * @param res content to write in the files.
 * @param file name of the 3d file given by the user.
 */
void writeInFile(string res, string file) {
    //generats XML file using tinyxml2
    char tmp[256];

    getcwd(tmp, 256); //tmp which contains the directory

    string path(tmp);

    int found = path.find("GENERATOR"); // finds generator's localization

    replace(path.begin(), path.end(), '\\', '/');
    path.erase(path.begin() + found, path.end());

    string pathXML = path + "MODELS/model.xml";

    strcpy(tmp, pathXML.c_str());


    string path3d = path + "MODELS/" + file;


    // Create and open a text file
    ofstream MyFile(path3d);

    // Write to the file
    MyFile << res;

    // Close the file
    MyFile.close();


    const char *c = file.c_str();

    XMLDocument doc;
    doc.LoadFile(tmp);
    XMLNode *pRoot = doc.FirstChild();

    XMLElement *pElement = doc.NewElement("model");
    pElement->SetAttribute("file", c);

    pRoot->InsertEndChild(pElement);

    doc.SaveFile(tmp);

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
 * Function that creates spheres.
 * @param params sphere's measures (radius, slices, stacks, file)
 * If the Function recieves 1 slice it's not possible to create a sphere.
 * If the Function recieves 1 stack it's not possible to create a sphere.
 * @return true - if everything goes as plan.
 */
bool creatSphere(vector<string> params) {
    float radius = stod(params[0]);
    int slices = stoi(params[1]);
    int stacks = stoi(params[2]);

    string file = params[3];
    int found = file.find(".3d");
    if (found <= 0) return false;

    if (radius < 0 || slices < 0 || stacks < 0) {
        return false;
    }

    int maxTrianglePoints = 6 * slices * (stacks - 1);
    string res = to_string(maxTrianglePoints) + "\n";

    //calcute height of each stack
    float stackHeight = (2 * radius) / stacks;
    //calcute angle of each slice
    float angle = (2 * M_PI) / slices;
    float stack = 0;
    //circunference of points, given slices, height and radius
    for (float i = radius; i > 0.000001; i -= stackHeight) {

        float stackRadius = sqrt(pow(radius, 2) - pow(i, 2));
        float stackRadius2 = sqrt(pow(radius, 2) - pow((i - stackHeight), 2));

        for (int c = 0; c < slices; c++) {

            float alpha = angle * c;
            float alpha2 = angle * (c + 1);
            float p1x = cos(alpha) * stackRadius;
            float p1y = i;
            float p1z = -sin(alpha) * stackRadius;
            float p2x = cos(alpha2) * stackRadius;
            //float p2y = i;
            float p2z = -sin(alpha2) * stackRadius;
            float p3x = cos(alpha) * stackRadius2;
            float p3y = (i - stackHeight);
            float p3z = -sin(alpha) * stackRadius2;
            float p4x = cos(alpha2) * stackRadius2;
            //float p4y = (i - stackHeight);
            float p4z = -sin(alpha2) * stackRadius2;

            // Normals
            float p1n[3] = { p1x, p1y, p1z};
            float p2n[3] = { p2x, p1y, p2z };
            float p3n[3] = { p3x, p3y, p3z };
            float p4n[3] = { p4x, p3y, p4z };
            normalize( p1n );
            normalize( p2n );
            normalize( p3n );
            normalize( p4n );


            string coordinatesYMinus = to_string((-1 * i));
            string coordinatesY3Minus = to_string((-1 * (i - stackHeight)));

            string p1 = to_string(p1x) + "," + to_string(p1y) + "," + to_string(p1z) + "," +
                        to_string(p1n[0]) + "," + to_string(p1n[1]) + "," + to_string(p1n[2]) + "," +
                        to_string(alpha / (2 * M_PI)) + "," + to_string(-1*stack /stacks ) + "\n";

            string p2 = to_string(p2x) + "," + to_string(p1y) + "," + to_string(p2z) + "," +
                        to_string(p2n[0]) + "," + to_string(p2n[1]) + "," + to_string(p2n[2]) + "," +
                        to_string(alpha2 / (2 * M_PI)) + "," + to_string( -1*(stack) /stacks ) + "\n";

            string p3 = to_string(p3x) + "," + to_string(p3y) + "," + to_string(p3z) + "," +
                        to_string(p3n[0]) + "," + to_string(p3n[1]) + "," + to_string(p3n[2]) + "," +
                        to_string(alpha / (2 * M_PI)) + "," + to_string(-1* (stack+1) / stacks) + "\n";

            string p4 = to_string(p4x) + "," + to_string(p3y) + "," + to_string(p4z) + "," +
                        to_string(p4n[0]) + "," + to_string(p4n[1]) + "," + to_string(p4n[2]) + "," +
                        to_string(alpha2 / (2 * M_PI)) + "," + to_string( -1* (stack + 1) / stacks ) + "\n";


            string p1Minus = to_string(p1x) + "," + to_string((-1 * i)) + "," + to_string(p1z) + "," +
                             to_string(p1n[0]) + "," + to_string(-p1n[1]) + "," + to_string(p1n[2]) + "," +
                             to_string(alpha / (2 * M_PI)) + "," + to_string(-1*(stacks - stack) / stacks) + "\n";

            string p2Minus = to_string(p2x) + "," + to_string((-1 * i)) + "," + to_string(p2z) + "," +
                             to_string(p2n[0]) + "," + to_string(-p2n[1]) + "," + to_string(p2n[2]) + "," +
                             to_string(alpha2 / (2 * M_PI)) + "," + to_string(-1*(stacks - stack) / stacks) + "\n";

            string p3Minus = to_string(p3x) + "," + coordinatesY3Minus + "," + to_string(p3z) + "," +
                             to_string(p3n[0]) + "," + to_string(-p3n[1]) + "," + to_string(p3n[2]) + "," +
                             to_string(alpha / (2 * M_PI)) + "," + to_string(-1*(stacks - (stack + 1))/stacks) + "\n";

            string p4Minus = to_string(p4x) + "," + coordinatesY3Minus + "," + to_string(p4z) + "," +
                             to_string(p4n[0]) + "," + to_string(-p4n[1]) + "," + to_string(p4n[2]) + "," +
                             to_string(alpha2 / (2 * M_PI)) + "," + to_string(-1*(stacks - (stack + 1)) / stacks) + "\n";

            //side
            if (i != radius) {
                res = res + p1 + p3 + p4 + p1 + p4 + p2;
                //mirroring
                if (!((stacks % 2 != 0) && (i - stackHeight) < 0))
                    res = res + p1Minus + p2Minus + p3Minus + p2Minus + p4Minus + p3Minus;
            }
                //top
            else {
                res = res + p1 + p3 + p4;
                res = res + p3Minus + p1Minus + p4Minus;
            }
        }
        stack++;
    }

    writeInFile(res, file);
    return true;
}

/**
 * Function that multiplies to vectors to create the normal vector
 * @param a vector a
 * @param b vector b
 * @return normal vector
 */
Point cross(Point a, Point b) {

    Point res;
    res.setX(a.getY()*b.getZ() - a.getZ()*b.getY());
    res.setY(a.getZ()*b.getX() - a.getX()*b.getZ());
    res.setZ(a.getX()*b.getY() - a.getY()*b.getX());

    return res;
}

/**
 * Function that normalizes a vector to create the normal vector
 * @param a vector
 */
void normalize(Point a) {

    float l = sqrt(a.getX()*a.getX() + a.getY()*a.getY()  + a.getZ()*a.getZ() );
    a.setX(a.getX()/l);
    a.setY(a.getY()/l);
    a.setZ(a.getZ()/l);
}


/*
*Function that creates a cone.
* @param params cone's measures (bottom radius, height, slices, stacks, file).
* If the Function recieves 1 slice it assumes a cone without any slices(or 1 slice).
* If the Function recieves 1 stack it assumes a cone without any stacks(or 1 stack).
* @return true - if everything goes as plan.
*/
bool creatCone(vector<string> params) {
    double radius = stod(params[0]);
    double height = stod(params[1]);
    int slices = stoi(params[2]);
    int stacks = stoi(params[3]);

    string res = to_string((2 * slices * stacks) * 3) + "\n";

    if (radius < 0 || height < 0 || slices < 0 || stacks < 0) {
        return false;
    }

    string file = params[4];
    int found = file.find(".3d");
    if (found <= 0) return false;

    //Calcute height of each stack
    double stackHeight = height / stacks;

    //Circunference of points, given slices height and radius
    for (double i = 0; i < stacks; i++) {

        double stackRadius = ((radius * (height - (stackHeight * i))) / height);
        double stackRadius2 = ((radius * (height - (stackHeight * (i + 1)))) / height);

        double angle = (2 * M_PI) / slices;

        for (int c = 0; c < slices; c++) {
            // Sides
            double alpha = angle * c;
            double alpha2 = angle * (c + 1);

            float p1x = cos(alpha) * stackRadius;
            float p1y = stackHeight * i;
            float p1z = -sin(alpha) * stackRadius;
            float p2x = cos(alpha2) * stackRadius;
            float p2y = stackHeight * i;
            float p2z = -sin(alpha2) * stackRadius;
            float p3x = cos(alpha) * stackRadius2;
            float p3y = stackHeight * (i + 1);
            float p3z = -sin(alpha) * stackRadius2;
            float p4x = cos(alpha2) * stackRadius2;
            float p4y = stackHeight * (i + 1);
            float p4z = -sin(alpha2) * stackRadius2;


            float p1n[3] = { p1x, p1y, p1z};
            float p2n[3] = { p2x, p2y, p2z };
            float p3n[3] = { p3x, p3y, p3z };
            float p4n[3] = { p4x, p4y, p4z };
            normalize( p1n );
            normalize( p2n );
            normalize( p3n );
            normalize( p4n );

            string p1 = to_string(p1x) + "," + to_string(p1y) + "," + to_string(p1z) + ",";
            string p1normal = to_string(p1n[0]) + "," + to_string(p1n[1]) + "," + to_string(p1n[2]) + ",";
            string p1texture = to_string(0.4375 + (0.1875 / stacks) * (stacks - i) * cos(alpha)) + "," +
                               to_string(0.1875 + (0.1875 / stacks) * (stacks - i) * sin(alpha)) + "\n";

            string p2 = to_string(p2x) + "," + to_string(p2y) + "," + to_string(p2z) + ",";
            string p2normal = to_string(p2n[0]) + "," + to_string(p2n[1]) + "," + to_string(p2n[2]) + ",";
            string p2texture = to_string(0.4375 + (0.1875 / stacks) * (stacks - i) * cos(alpha2)) + "," +
                               to_string(0.1875 + (0.1875 / stacks) * (stacks - i) * sin(alpha2)) + "\n";

            string p3 = to_string(p3x) + "," + to_string(p3y) + "," + to_string(p3z) + ",";
            string p3normal = to_string(p3n[0]) + "," + to_string(p3n[1]) + "," + to_string(p3n[2]) + ",";
            string p3texture = to_string(0.4375 + (0.1875 / stacks) * (stacks - (i + 1)) * cos(alpha)) + "," +
                               to_string(0.1875 + (0.1875 / stacks) * (stacks - (i + 1)) * sin(alpha)) + "\n";

            string p4 = to_string(p4x) + "," + to_string(p4y) + "," + to_string(p4z) + ",";
            string p4normal = to_string(p4n[0]) + "," + to_string(p4n[1]) + "," + to_string(p4n[2]) + ",";
            string p4texture = to_string(0.4375 + (0.1875 / stacks) * (stacks - (i + 1)) * cos(alpha2)) + "," +
                               to_string(0.1875 + (0.1875 / stacks) * (stacks - (i + 1)) * sin(alpha2)) + "\n";

            if (i == 0) {
                // Base
                string base = "0.000000,0.000000,0.000000,";
                string baseNormal = to_string(0) + "," + to_string(-1) + "," + to_string(0) + ",";
                string baseTextura = to_string(0.8125) + "," + to_string(0.1875) + "\n";

                string p1BaseNormal = to_string(0) + "," + to_string(-1) + "," + to_string(0) + ",";
                string p1BaseTextura = to_string(0.8125 + 0.1875 * cos(alpha)) + "," + to_string(0.1875 + 0.1875 * sin(alpha)) + "\n";

                string p2BaseNormal = to_string(0) + "," + to_string(-1) + "," + to_string(0) + ",";
                string p2BaseTextura =to_string(0.8125 + 0.1875 * cos(alpha2)) + "," + to_string(0.1875 + 0.1875 * sin(alpha2)) +"\n";

                res = res + p3 + p3normal + p3texture + p1 + p1normal + p1texture + p2 + p2normal + p2texture +
                      p3 + p3normal + p3texture + p2 + p2normal + p2texture + p4 + p4normal + p4texture;

                res = res + base + baseNormal + baseTextura + p2 + p2BaseNormal + p2BaseTextura + p1 + p1BaseNormal +
                      p1BaseTextura;

            } else if (i != (stacks - 1)) {
                res = res + p3 + p3normal + p3texture + p1 + p1normal + p1texture + p2 + p2normal + p2texture +
                      p3 + p3normal + p3texture + p2 + p2normal + p2texture + p4 + p4normal + p4texture;
            } else {

                string p3BaseNormal = to_string(0) + "," + to_string(1) + "," + to_string(0) + ",";
                string p3BaseTextura = to_string(0.4375) + "," + to_string(0.1875) + "\n";

                res = res + p3 + p3normal + p3texture + //p3BaseNormal + p3BaseTextura +
                        p1 + p1normal + p1texture + p2 + p2normal + p2texture;
            }
        }
    }
    writeInFile(res, file);
    return true;
}

/**
 * Function that transforms points into strings to form triangles.
 * @param pontos matrix with the several triangles points;
 * @param edges box's edges.
 * @return string containing the vertices.
 */
string buildTriangles(vector<vector<double>> points, vector<vector<double>> normals, vector<vector<double>> texture, double npoints) {

    string res = "";

    for (int j = 0; j < npoints; j++) {

        double x = points[j][0];
        double y = points[j][1];
        double z = points[j][2];

        double nx = normals[j][0];
        double ny = normals[j][1];
        double nz = normals[j][2];

        double tx = texture[j][0];
        double ty = texture[j][1];

        string coordinatesX = to_string(x);
        string coordinatesY = to_string(y);
        string coordinatesZ = to_string(z);

        string coordinatesnX = to_string(nx);
        string coordinatesnY = to_string(ny);
        string coordinatesnZ = to_string(nz);

        string coordinatestX = to_string(tx);
        string coordinatestY = to_string(ty);

        string coordinates = coordinatesX + "," + coordinatesY + "," + coordinatesZ + "," +
                coordinatesnX + "," + coordinatesnY + "," + coordinatesnZ + "," +
                coordinatestX + "," + coordinatestY + "\n";
        res = res + coordinates;
    }
    return res;
}

/**
 * Function that adds coordinates into the points vector
 * @param points vector where we add the coordinates;
 * @param i index;
 * @param x value of coordinate x;
 * @param y value of coordinate y;
 * @param z value of coordinate z;
 * @return vector after adding the coordinates;
 */
vector<vector<double>> addPoints(vector<vector<double>> points, int i, double x, double y, double z) {

    points[i][0] = x;
    points[i][1] = y;
    points[i][2] = z;
    return points;
}

/**
  * Function that creats planes in a 3d framework
  * @param lx firts positive value of x in the plane;
  * @param ly firts positive value of Y in the plane;
  * @param lz firts positive value of Z in the plane;
  * @param dx space between points in x axis;
  * @param dy space between points in y axis;
  * @param dz space between points in z axis;
  * @param edges the box's edges
  * @param face string containing the vertices
  * @return
  */
string buildPlanes(double lx, double ly, double lz, double dx, double dy, double dz, int edges, int face) {

    // number of points per plane
    int npoints = pow(edges, 2) * 2 * 3;

    //vector where to add the points
    vector<vector<double>> points(npoints, vector<double>(3));
    vector<vector<double>> normals(npoints, vector<double>(3));
    vector<vector<double>> texture(npoints, vector<double>(3));

    double p1, l1, d1;
    double p2, l2, d2;
    int signal;
    int order;
    int tx = 1;
    int ty = 1;

    //Defines which face we are building
    switch (face) {
        case 1: {
            p1 = lx;
            p2 = ly;

            l1 = lx;
            l2 = ly;

            d1 = dx;
            d2 = dy;

            //defines if the triangles in the plane rotate clockwise or anticlockwise
            if (lz >= 0) {
                signal = 1;
                order = 1;

            } else {
                signal = -1;
                order = -1;
            }
            break;
        }
        case 2: {
            p1 = ly;
            p2 = lz;

            l1 = ly;
            l2 = lz;

            d1 = dy;
            d2 = dz;

            if (lx >= 0) {
                signal = 1;
                order = 1;

            } else {
                signal = -1;
                order = -1;
            }
            break;
        }
        case 3: {

            p1 = lz;
            p2 = lx;

            l1 = lz;
            l2 = lx;

            d1 = dz;
            d2 = dx;

            if (ly >= 0) {
                signal = 1;
                order = 1;

            } else {
                signal = -1;
                order = -1;
            }
            break;
        }

    }

    int n = 0;
    int triangle = 1;
    int count = 0;

    // cycle that creats the coordinates and adds to the vector
    for (int i = 0; i < npoints; i++) {

        n++;
        // counts how many times the algorithm its the end of a row (the end of a plane)
        if ((((-l1) < p1 + 0.0001 && (-l1) > p1 - 0.0001) && order == 1) ||
            (((-l2) < p2 + 0.0001 && (-l2) > p2 - 0.0001) && order == -1)) {
            count++;
        }

        // changes the row if the count is 3
        if (count == 3) {

            //the row change depends if the triangles are build clockwise or not
            if (order == 1) {
                p1 = l1;
                p2 = p2 - d2;
            } else {
                p1 = p1 - d1;
                p2 = l2;

            }
            count = 0;
        }

        //adds coordinates in the vector
        if (face == 1) {
            points = addPoints(points, i, p1, p2, lz);
            if(order == -1) normals = addPoints(normals,i,0,0,-1);
            else normals = addPoints(normals,i,0,0,1);
            texture = addPoints(texture,i,tx,ty,0);
        }
        else if (face == 2) {
            points = addPoints(points, i, lx, p1, p2);
            if(order == -1) normals = addPoints(normals,i,0,-1,0);
            else normals = addPoints(normals,i,0,1,0);
            texture = addPoints(texture,i,tx,ty,0);
        }
        else if (face == 3) {
            points = addPoints(points, i, p2, ly, p1);
            if(order == -1) normals = addPoints(normals,i,-1,0,0);
            else normals = addPoints(normals,i,1,0,0);
            texture = addPoints(texture,i,tx,ty,0);
        }

        //after building 1 triangle
        if (n == 3) {
            //if its the first triangle in the square, it repeats the coordinates from the iteration before
            if ((i + 1) % 6 != 0) {
                i++;
                //adds coordinates in the vector
                if (face == 1) {
                    points = addPoints(points, i, p1, p2, lz);
                    if(order == -1) normals = addPoints(normals,i,0,0,-1);
                    else normals = addPoints(normals,i,0,0,1);
                    texture = addPoints(texture,i,tx,ty,0);
                }
                else if (face == 2) {
                    points = addPoints(points, i, lx, p1, p2);
                    if(order == -1) normals = addPoints(normals,i,0,-1,0);
                    else normals = addPoints(normals,i,0,1,0);
                    texture = addPoints(texture,i,tx,ty,0);
                }
                else if (face == 3) {
                    points = addPoints(points, i, p2, ly, p1);
                    if(order == -1) normals = addPoints(normals,i,-1,0,0);
                    else normals = addPoints(normals,i,1,0,0);
                    texture = addPoints(texture,i,tx,ty,0);
                }
                n = 1;
                triangle = 2;
            } else {
                n = 0;
                triangle = 1;
            }
        }

        //after we build a square (two triangles)
        if (i % 6 == 0 && i >= 6) {
            signal = signal * (-1);
        }

        //it builds the next coordinate depending if we are moving horizontally or vertically through the plane
        if (triangle == 1) {
            if (signal == 1) {
                p1 = p1 - d1;
                tx = tx - 1;
            } else {
                p2 = p2 - d2;
                ty = ty - 1;
            }
        } else {
            if (signal == 1) {
                p1 = p1 + d1;
                tx = tx + 1;
            } else {
                p2 = p2 + d2;
                ty = ty +1;
            }
        }

        // changes the way we are moving in the plane
        signal = signal * (-1);
    }
    return buildTriangles(points,normals,texture,npoints);
}

/**
* Function that creats a box.
* @param params box's measures (x,y,z).
* If the Function doesn't recieve edges it assumes a box without any edges (or 1 edge).
* It also recieves the file where to save the vertices.
* @return true - if everything goes as plan.
*/
bool creatBox(vector<string> params) {

    int edges;
    string file;

    if (params.size() == 5) {
        edges = stoi(params[3]);
        if (edges < 0) return false;
        file = params[4];
    } else {
        edges = 1;
        file = params[3];
    }

    int found = file.find(".3d");
    if (found <= 0) return false;

    double lx = stod(params[0]) / edges;
    double ly = stod(params[1]) / edges;
    double lz = stod(params[2]) / edges;

    double x = stod(params[0]) / 2;
    double y = stod(params[1]) / 2;
    double z = stod(params[2]) / 2;

    if (lx < 0 || ly < 0 || lz < 0 || x < 0 || y < 0 || z < 0) {
        return false;
    }

    int vertices = pow(edges, 2) * 36;

    string verticesStr = to_string(vertices);

    string res;

    res = res + verticesStr + "\n";

    //PLANE XY
    res = res + buildPlanes(x, y, z, lx, ly, lz, edges, 1);
    res = res + buildPlanes(x, y, -z, lx, ly, lz, edges, 1);

    //PLANE YX
    res = res + buildPlanes(x, y, z, lx, ly, lz, edges, 2);
    res = res + buildPlanes(-x, y, z, lx, ly, lz, edges, 2);

    //PLANE XZ
    res = res + buildPlanes(x, y, z, lx, ly, lz, edges, 3);
    res = res + buildPlanes(x, -y, z, lx, ly, lz, edges, 3);

    writeInFile(res, file);

    return true;
}

/**
 * Function that creats plane in the XZ plane.
 * @param params plane (square) measures. It also recieves the file where to save the vertices.
 * @return true - if everything goes as plan.
 */
bool creatPlane(vector<string> params) {

    double l = stod(params[0]) / 2;
    double edge = stod(params[0]);
    string res = "";

    if (l < 0 || edge < 0) {
        return false;
    }

    string file = params[1];
    int found = file.find(".3d");
    if (found <= 0) return false;

    res = res + "6\n";

    string res1 = buildPlanes(l, 0.0, l, edge, edge, edge, 1, 3);

    res = res + res1;

    writeInFile(res, params[1]);

    return true;
}

/**
 * Function that creats cylinder
 * @param params measures of the cylinder - radius, height, slices and the file where to put the coordinates
 * @return true - if everything goes as plan
 */
bool creatCylinder(vector<string> params) {

    double radius = stod(params[0]);
    double height = stod(params[1]) / 2;
    int slices = stoi(params[2]);

    if (radius < 0 || height < 0 || slices < 0) {
        return false;
    }

    string file = params[3];
    int found = file.find(".3d");
    if (found <= 0) return false;

    int npoints = slices * 3 * 4;
    string res = to_string(npoints) + "\n";

    float delta = (2 * M_PI) / slices;
    int n = 0;

    for (int i = 0; i < slices; i++) {
        float alfa = i * delta;
        float nextAlfa = delta + alfa;
        float p1x = radius * sin(alfa);
        float p1z = radius * cos(alfa);
        float p2x = radius * sin(nextAlfa);
        float p2z = radius * cos(nextAlfa);

        string top = to_string(0) + "," + to_string(height) + "," + to_string(0) + ","
                     + to_string(0) + "," + to_string(1) + "," + to_string(0) + ","
                     + to_string(0.4375) + "," + to_string(0.1875) + "\n"

                     + to_string(p1x) + "," + to_string(height) + "," + to_string(p1z) + ","
                     + to_string(0) + "," + to_string(1) + "," + to_string(0) + ","
                     + to_string(0.4375 + 0.1875* sin(alfa)) + "," + to_string(0.1875 + 0.1875*cos(alfa)) + "\n"

                     + to_string(p2x) + "," + to_string(height) + "," + to_string(p2z) + ","
                     + to_string(0) + "," + to_string(1) + "," + to_string(0) + ","
                     + to_string(0.4375 + 0.1875* sin(nextAlfa)) + "," + to_string(0.1875 + 0.1875*cos(nextAlfa)) + "\n";

        string bottom = to_string(0) + "," + to_string(-height) + "," + to_string(0) + ","
                        + to_string(0) + "," + to_string(-1) + "," + to_string(0) + ","
                        + to_string(0.8125) + "," + to_string(0.1875) + "\n"
                        + to_string(p2x) + "," + to_string(-height) + "," + to_string(p2z) + ","
                        + to_string(0) + "," + to_string(-1) + "," + to_string(0) + ","
                        + to_string(0.8125 + 0.1875*sin(nextAlfa)) + "," + to_string(0.1875 + 0.1875*cos(nextAlfa)) + "\n"
                        + to_string(p1x) + "," + to_string(-height) + "," + to_string(p1z) + ","
                        + to_string(0) + "," + to_string(-1) + "," + to_string(0) + ","
                        + to_string(0.8125 + 0.1875*sin(alfa)) + "," + to_string(0.1875 + 0.1875*cos(alfa)) + "\n";

        string side1 = to_string(p1x) + "," + to_string(-height) + "," + to_string(p1z) + ","
                        + to_string(sin(alfa)) + "," + to_string(0) + "," + to_string(cos(alfa)) +","
                        + to_string(i / static_cast<float>(slices)) + "," + to_string(0.375) + "\n"
                       + to_string(p2x) + "," + to_string(height) + "," + to_string(p2z) + ","
                       + to_string(sin(nextAlfa)) + "," + to_string(0) + "," + to_string(cos(nextAlfa)) + ","
                       + to_string((i+1) / static_cast<float>(slices)) + "," + to_string(1.0) + "\n"
                       + to_string(p1x) + "," + to_string(height) + "," + to_string(p1z) + ","
                       + to_string(sin(alfa)) + "," + to_string(0) + "," + to_string(cos(alfa)) + ","
                       + to_string(i / static_cast<float>(slices)) + "," + to_string(1.0) + "\n";

        string side2 = to_string(p1x) + "," + to_string(-height) + "," + to_string(p1z) + ","
                       + to_string(sin(alfa)) + "," + to_string(0) + "," + to_string(cos(alfa)) + ","
                       + to_string(i / static_cast<float>(slices)) + "," + to_string(0.375) + "\n"
                       + to_string(p2x) + "," + to_string(-height) + "," + to_string(p2z) + ","
                       + to_string(sin(nextAlfa)) + "," + to_string(0) + "," + to_string(cos(nextAlfa)) + ","
                       + to_string((i+1) / static_cast<float>(slices)) + "," + to_string(0.375) + "\n"
                       + to_string(p2x) + "," + to_string(height) + "," + to_string(p2z) + ","
                       + to_string(sin(nextAlfa)) + "," + to_string(0) + "," + to_string(cos(nextAlfa)) + ","
                       + to_string((i+1) / static_cast<float>(slices)) + "," + to_string(1.0) + "\n";

        res = res + top + bottom + side1 + side2;
    }

    writeInFile(res, file);
    return true;
}

/**
 * Functions that creates a ring (torus)
 * @param params measures of ring - inner radius, outter radius, slices
 * @return true - if everything goes as plan
 */
bool creatRing(vector<string> params) {

    double radiusInt = stod(params[0]);
    double radiusExt = stod(params[1]);
    double slices = stoi(params[2]);

    string file = params[3];
    int found = file.find(".3d");
    if (found <= 0) return false;

    string res = to_string(12 * slices) + "\n";

    if (radiusInt < 0 || radiusExt < 0 || slices < 0) {
        return false;
    }

    double angle = (2 * M_PI) / slices;


    for (int i = 0; i < slices; i++) {
        double alpha = angle * i;
        double alpha2 = angle * (i + 1);

        float int_x = cos(alpha) * radiusInt;
        float int_y = sin(alpha) * radiusInt;

        float ext_x = cos(alpha) * radiusExt;
        float ext_y = sin(alpha) * radiusExt;

        float next_int_x = cos(alpha2) * radiusInt;
        float next_int_y = sin(alpha2) * radiusInt;

        float next_ext_x = cos(alpha2) * radiusExt;
        float next_ext_y = sin(alpha2) * radiusExt;


        res += to_string(next_int_x) + "," + to_string(0) + "," + to_string(next_int_y) + "," +
               to_string(0) + "," + to_string(-1) + "," + to_string(0) + "," +
               to_string(cos(alpha2) * radiusInt/radiusExt) + "," + to_string(sin(alpha2) *radiusInt / radiusExt) + "\n";
        res += to_string(int_x) + "," + to_string(0) + "," + to_string(int_y) + "," +
               to_string(0) + "," + to_string(-1) + "," + to_string(0) + "," +
               to_string(cos(alpha) * radiusInt / radiusExt) + "," + to_string(sin(alpha) * radiusInt / radiusExt) + "\n";
        res += to_string(ext_x) + "," + to_string(0) + "," + to_string(ext_y) + "," +
               to_string(0) + "," + to_string(-1) + "," + to_string(0) + "," +
               to_string(cos(alpha)) + "," + to_string(sin(alpha)) + "\n";

        res += to_string(next_int_x) + "," + to_string(0) + "," + to_string(next_int_y) + "," +
               to_string(0) + "," + to_string(-1) + "," + to_string(0) + "," +
               to_string(cos(alpha2) * radiusInt / radiusExt) + "," + to_string(sin(alpha2) * radiusInt / radiusExt) + "\n";
        res += to_string(ext_x) + "," + to_string(0) + "," + to_string(ext_y) + "," +
               to_string(0) + "," + to_string(-1) + "," + to_string(0) +
               to_string(cos(alpha)) + "," + to_string(sin(alpha)) + "\n";
        res += to_string(next_ext_x) + "," + to_string(0) + "," + to_string(next_ext_y) + "," +
               to_string(0) + "," + to_string(-1) + "," + to_string(0) +
               to_string(cos(alpha2)) + "," + to_string(sin(alpha2)) + "\n";

        res += to_string(ext_x) + "," + to_string(0) + "," + to_string(ext_y) + "," +
               to_string(0) + "," + to_string(1) + "," + to_string(0) +
               to_string(cos(alpha)) + "," + to_string(sin(alpha)) + "\n";
        res += to_string(int_x) + "," + to_string(0) + "," + to_string(int_y) + "," +
               to_string(0) + "," + to_string(1) + "," + to_string(0) +
               to_string(cos(alpha) * radiusInt / radiusExt) + "," + to_string(sin(alpha) * radiusInt / radiusExt) + "\n";
        res += to_string(next_int_x) + "," + to_string(0) + "," + to_string(next_int_y) + "," +
               to_string(0) + "," + to_string(1) + "," + to_string(0) +
               to_string(cos(alpha2) * radiusInt / radiusExt) + "," + to_string(sin(alpha2) * radiusInt / radiusExt) +"\n";

        res += to_string(next_ext_x) + "," + to_string(0) + "," + to_string(next_ext_y) + "," +
               to_string(0) + "," + to_string(1) + "," + to_string(0) +
               to_string(cos(alpha2)) + "," + to_string(sin(alpha2)) + "\n";
        res += to_string(ext_x) + "," + to_string(0) + "," + to_string(ext_y) + "," +
               to_string(0) + "," + to_string(1) + "," + to_string(0) +
               to_string(cos(alpha)) + "," + to_string(sin(alpha)) + "\n";
        res += to_string(next_int_x) + "," + to_string(0) + "," + to_string(next_int_y) + "," +
               to_string(0) + "," + to_string(1) + "," + to_string(0) +
               to_string(cos(alpha2) * radiusInt / radiusExt) + "," + to_string(sin(alpha2) * radiusInt / radiusExt) + "\n";

    }

    writeInFile(res, file);
    return true;
}

/**
 * Function that splits a string by a delimiter
 * @param s string to split
 * @param del delimiter
 * @return a vector that stores the result string
 */
vector<string> tokenize(string s, string del) {
    vector<string> res;
    int start = 0;
    int end = s.find(del);
    while (end != -1) {
        res.push_back(s.substr(start, end - start));
        start = end + del.size();
        end = s.find(del, start);
    }
    res.push_back(s.substr(start, end - start));
    return res;
}

/**
 * Function transforms a vector of strings into a vector of integer
 * @param indices vector of strings
 * @return vector of integers
 */
vector<int> fromStringToInt(vector<string> indices) {

    vector<int> res;

    for (int i = 0; i < 16; i++) {
        res.push_back(stoi(indices[i]));
    }
    return res;
}

/**
 * Functions that adds two points
 * @param mult point
 * @param point  point
 * @return result
 */
Point sumPoints(Point mult, Point point) {

    Point res;

    res.setX(mult.getX()+point.getX());
    res.setY(mult.getY()+point.getY());
    res.setZ(mult.getZ()+point.getZ());

    return res;
}

/**
 * Function that multiplies two matrices
 * @param m float matrix
 * @param patch Point matrix
 * @return point matrix
 */
vector<vector<Point>> matrixMultiplication(vector<vector<float>> m, vector<vector<Point>> patch) {

    vector<vector<Point>> mult(4,vector<Point>(4));

    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 4; ++j) {

            mult[i][j].setX(0);
            mult[i][j].setY(0);
            mult[i][j].setZ(0);
        }
    }

    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 4; ++j){
            for (int k = 0; k < 4; ++k) {
                Point p = patch[k][j];
                float x = p.getX();
                float y = p.getY();
                float z = p.getZ();

                p.setX(x*m[i][k]);
                p.setY(y*m[i][k]);
                p.setZ(z*m[i][k]);

                Point pm = mult[i][j];

                mult[i][j] = sumPoints(pm,p);
            }
        }
    }
    return mult;
}

/**
 * Function that multiplies two matrices
 * @param result point matrix
 * @param m float matrix
 * @return point matrix
 */
vector<vector<Point>> matrix2Multiplication(vector<vector<Point>> result, vector<vector<float>> m) {

    vector<vector<Point>> mult(4,vector<Point>(4));

    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 4; ++j) {

            mult[i][j].setX(0);
            mult[i][j].setY(0);
            mult[i][j].setZ(0);
        }
    }

    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 4; ++j){
            for (int k = 0; k < 4; ++k) {
                Point p = result[i][k];

                float x = p.getX();
                float y = p.getY();
                float z = p.getZ();

                p.setX(x*m[k][j]);
                p.setY(y*m[k][j]);
                p.setZ(z*m[k][j]);

                Point pm = mult[i][j];

                mult[i][j] = sumPoints(pm,p);
            }
        }
    }
    return mult;

}

/**
 * Function that multiplies a vector by a matrix
 * @param u vector of floats
 * @param matrix matrix of Points
 * @return vector of points
 */
vector<Point> multiplyUbyMatrix(vector<float> u, vector<vector<Point>> matrix) {

    vector<Point> result(4);

    for (int j = 0; j < 4; ++j) {

        result[j].setX(0);
        result[j].setY(0);
        result[j].setZ(0);
    }

    for (int j = 0; j < 4; ++j) {
        for (int k = 0; k < 4; ++k) {

            Point p = matrix[k][j];
            float x = p.getX();
            float y = p.getY();
            float z = p.getZ();

            p.setX(x*u[k]);
            p.setY(y*u[k]);
            p.setZ(z*u[k]);

            Point pm = result[j];

            result[j] = sumPoints(pm,p);
        }
    }
    return  result;
}

/**
 * Function that multiplies two vectors
 * @param vector vector of floats
 * @param v vector of points
 * @return point
 */
Point multiplyVectors(vector<Point> vector, ::vector<float> v) {

    Point final;

    final.setX(0);
    final.setY(0);
    final.setZ(0);

    for(int j =0; j<4;++j){
        final.setX(final.getX()+vector[j].getX()*v[j]);
        final.setY(final.getY()+vector[j].getY()*v[j]);
        final.setZ(final.getZ()+vector[j].getZ()*v[j]);
    }
    return final;
}

/**
 * Functions that creates the u and v vectors
 * @param p seed that creates the vectors
 * @return the u or v vector
 */
vector<float> creatVectorUV(float p) {

    vector<float> u(4);
    u[0] = pow(p,3);
    u[1] = pow(p,2);
    u[2] = p;
    u[3] = 1;

    return u;
}

/**
 * Functions that initiates a matrix of points
 * @param tessellation the number of elements in a row or column
 * @return matrix of points
 */
vector<vector<Point>> initGrid(int tessellation) {

    vector<vector<Point>> grid(tessellation+1, vector<Point>(tessellation+1));

    for (int j = 0; j <= tessellation; j++) {

        for (int k = 0; k <= tessellation; k++) {

            grid[j][k].setX(0);
            grid[j][k].setY(0);
            grid[j][k].setZ(0);
        }
    }
    return grid;
}


vector<float> creatDerivVectorUV(float p) {

    vector<float> u(4);
    u[0] = 3*pow(p,2);
    u[1] = 2*p;
    u[2] = 1;
    u[3] = 0;

    return u;
}

/**
 * Functions that creates the points and organizes them in triangles in order to build the comet (teapot)
 * @param patches a set of bezier patches
 * @param tessellation number of tessellation in the comet (teapot)
 * @return vector of points that forms the comet (teapot)
 */
vector<vector<Point>> buildPointsComet(vector<vector<vector<Point>>> patches, int tessellation) {

    vector<vector<Point>> result;

    vector<Point> triangles;
    vector<Point> normals;
    vector<Point> textures; // a última coordenada fica a 0 - ignorada

    vector<vector<Point>> gridPoints = initGrid(tessellation);
    vector<vector<Point>> gridNorm = initGrid(tessellation);
    vector<vector<Point>> gridText = initGrid(tessellation);

    vector<vector<float>> m
    {
        {-1.0f,3.0f,-3.0f,1.0f},
        {3.0f,-6.0f,3.0f,0.0f},
        {-3.0f,3.0f,0.0f,0.0f},
        {1.0f,0.0f,0.0f,0.0f},
    };

    float pu = 0;
    float pv = 0;

    for(int i = 0; i<patches.size(); i++) {

        vector<vector<Point>> result = matrixMultiplication(m, patches[i]);
        vector<vector<Point>> matrix = matrix2Multiplication(result, m);

        for (int j = 0; j <= tessellation; j++) {

            pv = ((float) j) / ((float) tessellation);
            vector<float> v = creatVectorUV(pv);
            vector<float> derivV = creatDerivVectorUV(pv);

            for (int k = 0; k <= tessellation; k++) {

                pu = ((float) k) / ((float) tessellation);
                vector<float> u = creatVectorUV(pu);
                vector<float> derivU = creatDerivVectorUV(pu);

                vector<Point> res = multiplyUbyMatrix(u, matrix);
                Point final = multiplyVectors(res, v);

                gridPoints[j][k].setX(final.getX());
                gridPoints[j][k].setY(final.getY());
                gridPoints[j][k].setZ(final.getZ());

                vector<Point> resU = multiplyUbyMatrix(derivU, matrix);
                Point tagU = multiplyVectors(resU, v);

                vector<Point> resV = multiplyUbyMatrix(u, matrix);
                Point tagV = multiplyVectors(resV, derivV);

                Point norm = cross(tagV,tagU); // OU! pode estar mal - ao contrário
                normalize(norm);

                gridNorm[j][k].setX(norm.getX());
                gridNorm[j][k].setY(norm.getY());
                gridNorm[j][k].setZ(norm.getZ());

                gridText[j][k].setX(pu);
                gridText[j][k].setY(pv);
                gridText[j][k].setZ(0);
            }
        }

        for (int j = 0; j < tessellation; j++) {
            for (int k = 0; k < tessellation; k++) {

                triangles.push_back(gridPoints[j][k]);
                normals.push_back(gridNorm[j][k]);
                textures.push_back(gridText[j][k]);

                triangles.push_back(gridPoints[j+1][k]);
                normals.push_back(gridNorm[j+1][k]);
                textures.push_back(gridText[j+1][k]);

                triangles.push_back(gridPoints[j][k+1]);
                normals.push_back(gridNorm[j][k+1]);
                textures.push_back(gridText[j][k+1]);

                triangles.push_back(gridPoints[j+1][k]);
                normals.push_back(gridNorm[j+1][k]);
                textures.push_back(gridText[j+1][k]);

                triangles.push_back(gridPoints[j+1][k+1]);
                normals.push_back(gridNorm[j+1][k+1]);
                textures.push_back(gridText[j+1][k+1]);

                triangles.push_back(gridPoints[j][k+1]);
                normals.push_back(gridNorm[j][k+1]);
                textures.push_back(gridText[j][k+1]);
            }
        }
    }

    result.push_back(triangles);
    result.push_back(normals);
    result.push_back(textures);

    return result;
}


/**
 * Functions that creates the comet
 * @param params measures of the comet (teapot): file to read the patches, tessellation, file to store the points
 * @return true - if everything goes as plan
 */
bool creatComet(vector<string> params) {

    char tmp[256];

    getcwd(tmp, 256); //tmp which contains the directory

    string path(tmp);

    int found = path.find("GENERATOR"); // finds generator's localization

    replace(path.begin(), path.end(), '\\', '/');
    path.erase(path.begin() + found, path.end());

    string pathFile = path + "MODELS/" + params[0];

    ifstream MyFile(pathFile);
    string number;
    int i = 0;
    int numberPatch;
    int numberControl = 0;
    vector<string> patchesIndices;
    vector<Point> points;
    vector<vector<vector<Point>>> patches;
    int tessellation = stoi(params[1]);
    string saveFile = params[2];

    while (getline(MyFile, number)) {

        if (i == 0) {
            numberPatch = stoi(number);

        } else if (i <= numberPatch) {
            patchesIndices.push_back(number);

        } else if (i == numberPatch + 1) {
            numberControl = stoi(number);

        } else if(i > numberPatch+1) {
            number = number.substr(number.find(" ") + 1);
            vector<string> res = tokenize(number, ", ");

            Point p;
            p.setX(stof(res[0]));
            p.setY(stof(res[1]));
            p.setZ(stof(res[2]));

            points.push_back(p);
        }
        i++;
    }


    for (int n = 0; n < numberPatch; n++) {

        int count = 0;

        vector<Point> curve(4);
        vector<vector<Point>> patch;

        vector<string> res = tokenize(patchesIndices[n], ", ");
        vector<int> indices = fromStringToInt(res);

        for (int i = 0; i < indices.size(); i++) {
            int index = indices[i];
            Point p = points[index];

            if(count<4){
                curve[count] = p;

                if(i== indices.size()-1){
                    patch.push_back(curve);
                }

            }else{
                count = 0;
                patch.push_back(curve);
                curve[count] = p;
            }
            count++;
        }

        patches.push_back(patch);
    }

    vector<vector<Point>> vectors = buildPointsComet(patches,tessellation);
    string res;

    vector<Point> triangles = vectors[0];
    vector<Point> normals = vectors[1];
    vector<Point> textures = vectors[2];

    res = to_string(triangles.size())+"\n";

    for(int j =0; j<triangles.size(); j++) {

        Point p = triangles[j];
        Point normp = normals[j];
        Point texp = textures[j];

        string x = to_string(p.getX());
        string y = to_string(p.getY());
        string z = to_string(p.getZ());

        string nx = to_string(normp.getX());
        string ny = to_string(normp.getY());
        string nz = to_string(normp.getZ());

        string tx = to_string(texp.getX());
        string ty = to_string(texp.getY());

        res = res + x + ", " + y + ", " + z + ", " +
                nx + ", " + ny + ", " + nz + ", " +
                tx + ", " + ty + "\n";
    }

    writeInFile(res, saveFile);

    return true;
}

/**
 * Functions that creates points
 * @param vector with the parameters: inner radius, outer radius, number of points, file to store the points
 * @return true - if everything goes as plan
 */
bool creatPoints(vector<string> params) {
    string file = params[3];
    int raio = stoi(params[0]);
    int max, min, nrPoints;

    string res;

    nrPoints = stod(params[2]);
    max = stoi(params[1]);
    min = -max;

    float x, z;

    float distance = raio;
    srand(time(NULL));

    for (int i = 0; i < nrPoints; i++) {

        x = min + static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / (max - min)));
        z = min + static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / (max - min)));
        distance = sqrt(pow(x, 2) + pow(z, 2));

        while (distance <= raio || distance >= (max)) {
            x = min + static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / (max - min)));
            z = min + static_cast <float> (rand()) / (static_cast <float> (RAND_MAX / (max - min)));

            distance = sqrt(pow(x, 2) + pow(z, 2));
        }

        res = res + to_string(x) + ",0," + to_string(z) + "\n";

    }
    int tam = count(res.begin(), res.end(), '\n');
    string resultado = "ring\n";
    resultado = resultado + to_string(tam) + "\n" + res;

    writeInFile(resultado, file);

    return true;
}

/**
 * Function that defines which primitive the user wants to generate.
 * @param primitive name of the primitive;
 * @param params primitive measures including the file to save the coordinates.
 * @return true - if everything goes as plan.
 */
bool parseInput(string primitive, vector<string> params) {

    bool ret;

    if (primitive.compare("box") == 0) {
        if (params.size() == 4 || params.size() == 5) {
            ret = creatBox(params);
        } else ret = false;
    } else if (primitive.compare("cone") == 0) {
        if (params.size() == 5) {
            ret = creatCone(params);
        } else ret = false;
    } else if (primitive.compare("plane") == 0) {
        if (params.size() == 2) {
            ret = creatPlane(params);
        } else ret = false;
    } else if (primitive.compare("sphere") == 0) {
        if (params.size() == 4) {
            ret = creatSphere(params);
        } else ret = false;
    } else if (primitive.compare("cylinder") == 0) {
        if (params.size() == 4) {
            ret = creatCylinder(params);
        } else ret = false;
    } else if (primitive.compare("ring") == 0) {
        if (params.size() == 4) {
            ret = creatRing(params);
        } else ret = false;
    } else if (primitive.compare("comet") == 0) {
        if (params.size() == 3) {
            ret = creatComet(params);
        } else ret = false;
    } else if (primitive.compare("points") == 0) {
        if (params.size() == 4) {
            ret = creatPoints(params);
        }
        else ret = false;
    }
    return ret;
}

/**
 * Function that iniciates the Generator.
 */
int main(int argc, char **argv) {

    if (argc == 1) {
        cout << "Not enough arguments";
        return 1;
    }

    string primitive(argv[1]);
    vector<string> params;
    transform(primitive.begin(), primitive.end(), primitive.begin(), ::tolower);

    for (int i = 2; i < argc; i++) {
        params.push_back(argv[i]);
    }

    if (!parseInput(primitive, params)) {
        cout << "Argumentens for are invalid";
    }

    return 1;
}