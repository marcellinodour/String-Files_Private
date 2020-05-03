package io.github.oliviercailloux.samples.string_files;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

public class MyStringFilesUtils implements StringFilesUtils {
	private String stringLocalReferenceFolder;
	private Path pathLocalReferenceFolder;

	public static MyStringFilesUtils newInstance() {
		return new MyStringFilesUtils();
	}

	private MyStringFilesUtils() {
		this.stringLocalReferenceFolder = ".";
		this.pathLocalReferenceFolder = Path.of(".");
	}

	@Override
	public boolean setReferenceFolder(Path referenceFolder) throws IOException {

		if (referenceFolder.normalize().equals(Path.of(stringLocalReferenceFolder).normalize())) {
			return false;
		}

		String pathFolder = referenceFolder.toString();

		this.pathLocalReferenceFolder = referenceFolder;
		this.stringLocalReferenceFolder = pathFolder;

		return true;

	}

	@Override
	public String getAbsolutePath(String pathRelativeToReference) {
		Path path = Path.of(pathRelativeToReference);

		this.pathLocalReferenceFolder = path.toAbsolutePath();

		return this.pathLocalReferenceFolder.toString();
	}

	@Override
	public ImmutableList<String> getContentUsingIso88591Charset(String pathRelativeToReference) throws IOException {

		Path filePath = this.pathLocalReferenceFolder.resolve(pathRelativeToReference);

		List<String> listReturn = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);

		return ImmutableList.copyOf(listReturn);

	}

	@Override
	public String getPathRelativeToReference(String pathRelativeToCurrent) {
		Path relativeToCurrent = Path.of(pathRelativeToCurrent);

		return this.pathLocalReferenceFolder.relativize(relativeToCurrent).toString();
	}
}
