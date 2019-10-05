//**************************************************************************************************
// Copyright <2019> <DAN MOTA>
//
// Permission to use, copy, modify, and/or distribute this software for any purpose with or without
// fee is hereby granted, provided that the above copyright notice and this permission notice appear
// in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
// SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
// AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
// WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
// NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
// OF THIS SOFTWARE.
//**************************************************************************************************

package com.example.bmc.auxiliary;

import java.io.Serializable;

public class Building implements Serializable {
    private int buildingID;
    private String buildingName;
    private String buildingAddress;
    private String buildingLocation;
    private int buildingYearBuilt;

    public Building( int buildingID, String buildingName, String buildingAddress, String
            buildingLocation, int buildingYearBuilt ) {
        this.buildingID = buildingID;
        this.buildingName = buildingName;
        this.buildingAddress = buildingAddress;
        this.buildingLocation = buildingLocation;
        this.buildingYearBuilt = buildingYearBuilt;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public String getBuildingLocation() {
        return buildingLocation;
    }

    public int getBuildingYearBuilt() {
        return buildingYearBuilt;
    }

}
