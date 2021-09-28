#define _USE_MATH_DEFINES
#include <stdlib.h>
#include <string>
#include <algorithm>
#include <vector>
#include <cmath>
#include <iostream>
#include <fstream>
#include "tinyxml2.h"
#include <cstring>

#ifdef __linux__
#include <unistd.h>
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
void writeInFile(string res, string file){
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
    

    const char * c = file.c_str();

    XMLDocument doc;
    doc.LoadFile(tmp);
    XMLNode* pRoot = doc.FirstChild();

    XMLElement* pElement = doc.NewElement("model");
    pElement->SetAttribute("file", c);

    pRoot->InsertEndChild(pElement);

    doc.SaveFile(tmp);

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
     if(found <= 0) return false;

     if(radius<0 || slices<0 || stacks < 0){
         return false;
     }

    int maxTrianglePoints = 6 * slices * (stacks - 1);
    string res = to_string(maxTrianglePoints) + "\n";
    float angle = (2 * M_PI) / slices;

    //calcute height of each stack
    float stackHeight = (2 * radius) / stacks;
    
    //circunference of points, given slices height and radius
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

            string coordinatesYMinus = to_string((-1 * i));
            string coordinatesY3Minus = to_string((-1 * (i - stackHeight)));

            string p1 = to_string(p1x) + "," + to_string(p1y) + "," + to_string(p1z) + "\n";
            string p2 = to_string(p2x) + "," + to_string(p1y) + "," + to_string(p2z) + "\n";
            string p3 = to_string(p3x) + "," + to_string(p3y) + "," + to_string(p3z) + "\n";
            string p4 = to_string(p4x) + "," + to_string(p3y) + "," + to_string(p4z) + "\n";

            string p1Minus = to_string(p1x) + "," +  to_string((-1 * i)) + "," + to_string(p1z) + "\n";
            string p2Minus = to_string(p2x) + "," + to_string((-1 * i)) + "," + to_string(p2z) + "\n";
            string p3Minus = to_string(p3x) + "," + coordinatesY3Minus + "," + to_string(p3z) + "\n";
            string p4Minus = to_string(p4x) + "," + coordinatesY3Minus + "," + to_string(p4z) + "\n";

            //side
            if (i != radius) {
                res = res + p1 + p3 + p4 + p1 + p4 + p2;
                //mirroring
                if (!((stacks % 2 != 0) && (i - stackHeight) < 0)) res = res + p1Minus + p2Minus + p3Minus + p2Minus + p4Minus + p3Minus;
            }
            //top
            else {
                res = res + p1 + p3 + p4;
                res = res + p3Minus + p1Minus + p4Minus;
            }
        }
    }

    writeInFile(res, file);
    return true;
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

    if(radius<0 || height <0 || slices<0 || stacks < 0){
        return false;
    }

    string file = params[4];
    int found = file.find(".3d");
    if(found <= 0) return false;

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
          
            string p1 = to_string(p1x) + "," + to_string(p1y) + "," + to_string(p1z) + "\n";
            string p2 = to_string(p2x) + "," + to_string(p2y) + "," + to_string(p2z) + "\n";
            string p3 = to_string(p3x) + "," + to_string(p3y) + "," + to_string(p3z) + "\n";
            string p4 = to_string(p4x) + "," + to_string(p4y) + "," + to_string(p4z) + "\n";
            
            if (i == 0) {
                // Base
                string base = "0.000000,0.000000,0.000000\n";
                res = res + p3 + p1 + p2 + p3 + p2 + p4;
                res = res + base + p2 + p1;
            }
            else if (i != (stacks - 1)) {
                res = res + p3 + p1 + p2 + p3 + p2 + p4;
            }
            else res = res + p3 + p1 + p2;
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
string buildTriangles(vector<vector<double>> points, double npoints) {

    string res = "";

    for (int j = 0; j < npoints; j++) {

        double x = points[j][0];
        double y = points[j][1];
        double z = points[j][2];

        string coordinatesX = to_string(x);
        string coordinatesY = to_string(y);
        string coordinatesZ = to_string(z);
        string coordinates = coordinatesX + "," + coordinatesY + "," + coordinatesZ + "\n";
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
    int npoints = pow(edges,2)*2*3;

    //vector where to add the points
    vector<vector<double>> points(npoints,vector<double>(3));

    double p1,l1,d1;
    double p2,l2,d2;
    int signal;
    int order;

    //Defines which face we are building
    switch(face) {
        case 1:{
            p1 = lx;
            p2 = ly;

            l1 = lx;
            l2 = ly;

            d1 = dx;
            d2 = dy;

            //defines if the triangles in the plane rotate clockwise or anticlockwise
            if(lz>=0){
                signal = 1;
                order = 1;

            } else{
                signal = -1;
                order = -1;
            }
            break;
        }
        case 2:{
            p1 = ly;
            p2 = lz;

            l1 = ly;
            l2 = lz;

            d1 = dy;
            d2 = dz;

            if(lx>=0){
                signal = 1;
                order = 1;

            } else{
                signal = -1;
                order = -1;
            }
            break;
        }
        case 3:{

            p1 = lz;
            p2 = lx;

            l1 = lz;
            l2 = lx;

            d1 = dz;
            d2 = dx;

            if(ly>=0){
                signal = 1;
                order = 1;

            } else{
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
    for(int i = 0; i<npoints; i++){

        n++;
        // counts how many times the algorithm its the end of a row (the end of a plane)
        if((((-l1) < p1 + 0.0001 && (-l1) > p1 - 0.0001) && order == 1) ||(((-l2) < p2 + 0.0001 && (-l2) > p2 - 0.0001) && order == -1)) {
            count++;
        }

        // changes the row if the count is 3
        if(count == 3) {

            //the row change depends if the triangles are build clockwise or not
            if(order == 1) {
                p1 = l1;
                p2 = p2 - d2;
            }
            else{
                p1 = p1 - d1;
                p2= l2;
            }
            count = 0;
        }

        //adds coordinates in the vector
        if(face == 1){points = addPoints(points,i,p1,p2,lz);}
        else if(face == 2){points = addPoints(points,i,lx,p1,p2);}
        else if(face == 3){points = addPoints(points,i,p2,ly,p1);}

        //after building 1 triangle
        if (n==3) {
            //if its the first triangle in the square, it repeats the coordinates from the iteration before
            if ((i+1) % 6 != 0) {
                i++;
                if(face == 1){points = addPoints(points,i,p1,p2,lz);}
                else if(face == 2){points = addPoints(points,i,lx,p1,p2);}
                else if(face == 3){points = addPoints(points,i,p2,ly,p1);}
                n = 1;
                triangle = 2;
            }
            else{ n=0;triangle = 1;}
        }

        //after we build a square (two triangles)
        if (i%6==0 && i >= 6) {
            signal=signal*(-1);}

        //it builds the next coordinate depending if we are moving horizontally or vertically through the plane
        if(triangle == 1) {
            if(signal == 1){
                p1 = p1-d1;
            } else p2 = p2-d2;
        } else {
            if(signal == 1){
                p1 = p1+d1;
            } else p2 = p2+d2;
        }

        // changes the way we are moving in the plane
        signal=signal*(-1);
    }
    return buildTriangles(points, npoints);
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
        if(edges < 0) return false;
        file = params[4];
    }
    else {
        edges = 1;
        file = params[3];
    }

    int found = file.find(".3d");
    if(found <= 0) return false;

    double lx = stod(params[0]) / edges;
    double ly = stod(params[1]) / edges;
    double lz = stod(params[2]) / edges;

    double x = stod(params[0]) / 2;
    double y = stod(params[1]) / 2;
    double z = stod(params[2]) / 2;

    if(lx<0 || ly<0 || lz<0 || x<0 || y<0 || z<0){
        return false;
    }

    int vertices = pow(edges, 2) * 36;

    string verticesStr = to_string(vertices);

    string res;

    res = res + verticesStr + "\n";

    //PLANE XY
    res = res + buildPlanes(x, y, z, lx, ly, lz, edges,1);
    res = res + buildPlanes(x, y, -z, lx, ly,lz, edges,1);

    //PLANE YX
    res = res + buildPlanes(x, y, z, lx, ly, lz, edges,2);
    res = res + buildPlanes(-x, y, z, lx, ly, lz, edges,2);

    //PLANE XZ
    res = res + buildPlanes(x, y, z, lx, ly,lz, edges,3);
    res = res + buildPlanes(x, -y, z, lx, ly,lz, edges,3);

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

    if(l<0 || edge<0){
        return false;
    }

    string file = params[1];
    int found = file.find(".3d");
    if(found <= 0) return false;

    res = res + "6\n";

    string res1 = buildPlanes(l, 0.0, l, edge, edge, edge, 1,3);

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

    if(radius<0 || height <0 || slices<0){
        return false;
    }

    string file = params[3];
    int found = file.find(".3d");
    if(found <= 0) return false;

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

        string top = to_string(0) + "," + to_string(height) + "," + to_string(0) + "\n"
                     + to_string(p1x) + "," + to_string(height) + "," + to_string(p1z) + "\n"
                     + to_string(p2x) + "," + to_string(height) + "," + to_string(p2z) + "\n";

        string bottom = to_string(0) + "," + to_string(-height) + "," + to_string(0) + "\n"
                        + to_string(p2x) + "," + to_string(-height) + "," + to_string(p2z) + "\n"
                        + to_string(p1x) + "," + to_string(-height) + "," + to_string(p1z) + "\n";

        string side1 = to_string(p1x) + "," + to_string(-height) + "," + to_string(p1z) + "\n"
                       + to_string(p2x) + "," + to_string(height) + "," + to_string(p2z) + "\n"
                       + to_string(p1x) + "," + to_string(height) + "," + to_string(p1z) + "\n";

        string side2 = to_string(p1x) + "," + to_string(-height) + "," + to_string(p1z) + "\n"
                       + to_string(p2x) + "," + to_string(-height) + "," + to_string(p2z) + "\n"
                       + to_string(p2x) + "," + to_string(height) + "," + to_string(p2z) + "\n";

        res = res + top + bottom + side1 + side2;
    }

    writeInFile(res, file);
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
        if(params.size()==4 || params.size()==5) {
            ret = creatBox(params);
        } else ret = false;
    }
    else if (primitive.compare("cone") == 0) {
        if(params.size()==5) {
            ret = creatCone(params);
        } else ret = false;
    }
    else if (primitive.compare("plane") == 0) {
        if(params.size()==2) {
            ret = creatPlane(params);
        } else ret = false;
    }
    else if (primitive.compare("sphere") == 0) {
        if(params.size()==4) {
            ret = creatSphere(params);
        } else ret = false;
    }
    else if (primitive.compare("cylinder") == 0) {
        if(params.size()==4) {
            ret = creatCylinder(params);
        } else ret = false;
    }

    return ret;
}

/**
 * Function that iniciates the Generator.
 */
int main(int argc, char **argv) {

    if(argc == 1)  {
        cout << "Not enough arguments";
        return 1;
    }

    string primitive(argv[1]);
    vector<string> params;
    transform(primitive.begin(), primitive.end(), primitive.begin(), ::tolower);

    for (int i = 2; i < argc; i++) {
        params.push_back(argv[i]);
    }

    if(!parseInput(primitive, params)){
        cout<< "Argumentens for are invalid";
    }

	return 1;
}