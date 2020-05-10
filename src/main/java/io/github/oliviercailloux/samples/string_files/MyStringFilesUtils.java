package io.github.oliviercailloux.samples.string_files;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class MyStringFilesUtils implements StringFilesUtils {
	private Path pathLocalReferenceFolder;

	public static MyStringFilesUtils newInstance() {
		return new MyStringFilesUtils();
	}

	private MyStringFilesUtils() {
		this.pathLocalReferenceFolder = Path.of(".");
	}

	@Override
	public boolean setReferenceFolder(Path referenceFolder) throws IOException {
		
		checkNotNull(referenceFolder, "This argument cannot be null");
		
		if (referenceFolder.normalize().equals(this.pathLocalReferenceFolder.normalize())) {
			return false;
		}
		
		this.pathLocalReferenceFolder = referenceFolder;

		return true;

	}

	@Override
	public String getAbsolutePath(String pathRelativeToReference) {
		
		checkNotNull(pathRelativeToReference, "This argument cannot be null");
		
		if(Path.of(pathRelativeToReference).isAbsolute()) {
			throw new IllegalArgumentException("This argument cannot be an absolute path");
		}
		
		if(pathRelativeToReference.equals(".") || pathRelativeToReference.equals("")) {
			return this.pathLocalReferenceFolder.toAbsolutePath().toString();
		}
		
		return this.pathLocalReferenceFolder.resolve(pathRelativeToReference).toAbsolutePath().toString();
		
	}

	@Override
	public ImmutableList<String> getContentUsingIso88591Charset(String pathRelativeToReference) throws IOException {
		
		checkNotNull(pathRelativeToReference, "This argument cannot be null");
		
		if(Path.of(pathRelativeToReference).isAbsolute()) {
			throw new IllegalArgumentException("This argument cannot be an absolute path");
		}

		Path filePath = this.pathLocalReferenceFolder.resolve(pathRelativeToReference);
		
		List<String> listReturn = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);

		return ImmutableList.copyOf(listReturn);

	}

	@Override
	public String getPathRelativeToReference(String pathRelativeToCurrent) {
		
		checkNotNull(pathRelativeToCurrent, "This argument cannot be null");
		
		if(pathRelativeToCurrent.equals(".") || pathRelativeToCurrent.equals("")) {
			return this.pathLocalReferenceFolder.relativize(Path.of(".")).toString();
		}
		
		if(Path.of(pathRelativeToCurrent).isAbsolute()) {
			throw new IllegalArgumentException("This argument cannot be an absolute path");
		}
		
		Path relativeToCurrent = Path.of(pathRelativeToCurrent);

		return this.pathLocalReferenceFolder.relativize(relativeToCurrent).toString();
	}
}
