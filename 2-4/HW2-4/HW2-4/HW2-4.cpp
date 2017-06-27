// Written by Eric in 2017

#include <iostream>
#include <ctime>
#include <cassert>
#include <chrono>
#include <queue>
using namespace std;

// WINDOWS ONLY //
// get size of active memory set
#include <windows.h>
#include <psapi.h>
unsigned long long AvailRam()
{
     MEMORYSTATUSEX memInfo;
     memInfo.dwLength = sizeof(MEMORYSTATUSEX);
     GlobalMemoryStatusEx(&memInfo);
     return memInfo.ullAvailPhys;
}

void algorithm(unsigned long long M)
{
     chrono::time_point<chrono::steady_clock> timea{};
     chrono::time_point<chrono::steady_clock> timeb{};
     long long time_taken{};

     cout << "M = " << M << endl;

     cout << "n" << "\t" << "allocate" << "\t" << "algorithm" << "\t" << "deallocate" << endl;
     queue<double> c({ 0.5,0.6,0.7,0.8,0.9,0.95,0.99,1.0,1.01,1.1,1.5,2.0,5.0,10.0,50.0 });
     while ( c.empty() == false )
     {
          double n = c.front();
          c.pop();

          cout << n << "\t";
          unsigned long long C = (unsigned long long)(n * M / 4);
          unsigned long long m = C;

          // Allocate Arrays
          timea = chrono::steady_clock::now();
          int * A = new int[C] {};
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);
          cout << "\t\t";

          // Run Algorithm
          unsigned long long t = 0; // current step
          unsigned long long i = 0; // row index
          unsigned long long j = 0; // col index
          timea = chrono::steady_clock::now();
          while ( t < m )
          {
               A[t] = (int)t;
               ++t;
          }
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);
          cout << "\t\t";

          // Deallocate Arrays
          timea = chrono::steady_clock::now();
          delete[] A;
          timeb = chrono::steady_clock::now();
          time_taken = chrono::duration_cast<chrono::milliseconds>(timeb - timea).count();
          cout << (time_taken / 1000.0f);
          cout << "\t\t" << "sec";
          cout << endl;
     }
     cout << endl << endl;
}

int main()
{
     unsigned long long M = AvailRam();
     algorithm(M);

     return 0;
}
