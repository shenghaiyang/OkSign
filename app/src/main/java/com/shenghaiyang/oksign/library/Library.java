package com.shenghaiyang.oksign.library;

final class Library {
  final String name;
  final String url;
  final String licenseName;
  final String licenseContent;

  Library(String name, String url, String licenseName, String licenseContent) {
    this.name = name;
    this.url = url;
    this.licenseName = licenseName;
    this.licenseContent = licenseContent;
  }
}
